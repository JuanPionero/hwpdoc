package ext.org.apache.poi.hpsf;

import ext.org.apache.poi.hpsf.wellknown.SectionIDMap;
import org.apache.commons.io.input.UnsynchronizedByteArrayInputStream;
import org.apache.commons.io.output.UnsynchronizedByteArrayOutputStream;
import org.apache.poi.EmptyFileException;
//import org.apache.poi.hpsf.*;
import org.apache.poi.hpsf.*;
import org.apache.poi.hpsf.wellknown.PropertyIDMap;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.util.CodePageUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.LittleEndian;
import org.apache.poi.util.LittleEndianByteArrayInputStream;
import org.apache.poi.util.LittleEndianConsts;
import org.apache.poi.util.LittleEndianOutputStream;

import ext.org.apache.poi.hpsf.Section;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ext.org.apache.poi.hhwpf.Specification.DOCUMENT_SUMMARY_INFORMATION_ID_STR;

/**
 * TODO org.apache.poi.hpsf.PropertySet 과 org.apache.poi.hpsf.PropertySetFactory 참고해서 다시 작성할 예정
 */
public class HwpPropertySet  {
    public static final int OS_WIN16     = 0x0000;

    /**
     * If the OS version field holds this value the property set stream was
     * created on a Macintosh system.
     */
    public static final int OS_MACINTOSH = 0x0001;

    /**
     * If the OS version field holds this value the property set stream was
     * created on a 32-bit Windows system.
     */
    public static final int OS_WIN32     = 0x0002;
    /**
     * The "byteOrder" field must equal this value.
     */
    /* package */ static final int BYTE_ORDER_ASSERTION = 0xFFFE;

    /**
     * The "format" field must equal this value.
     */
    /* package */ static final int FORMAT_ASSERTION = 0x0000;

    /**
     * The length of the property set stream header.
     */
    /* package */ static final int OFFSET_HEADER =
            LittleEndianConsts.SHORT_SIZE + /* Byte order    */
            LittleEndianConsts.SHORT_SIZE + /* Format        */
            LittleEndianConsts.INT_SIZE +   /* OS version    */
            ClassID.LENGTH +                /* Class ID      */
            LittleEndianConsts.INT_SIZE;    /* Section count */

    public static boolean isPropertySetStream(final InputStream stream) throws IOException {
        /*
         * Read at most this many bytes.
         */
        final int BUFFER_SIZE = 50;

        /*
         * Read a couple of bytes from the stream.
         */
        try {
            final byte[] buffer = IOUtils.peekFirstNBytes(stream, BUFFER_SIZE);
            return isPropertySetStream(buffer, 0, buffer.length);
        } catch (EmptyFileException e) {
            return false;
        }
    }
    public static boolean isPropertySetStream(final byte[] src, final int offset, final int length) {
        LittleEndianByteArrayInputStream leis = new LittleEndianByteArrayInputStream(src, offset, length);

        /*
         * Read the header fields of the stream. They must always be
         * there.
         */
        try {
            final int byteOrder = leis.readUShort();
            if (byteOrder != BYTE_ORDER_ASSERTION) {
                return false;
            }
            final int format = leis.readUShort();
            if (format != FORMAT_ASSERTION) {
                return false;
            }
            final long osVersion = leis.readUInt();
            if (leis.skip(ClassID.LENGTH) != ClassID.LENGTH) {
                return false;
            }
            final long sectionCount = leis.readUInt();
            return (sectionCount >= 0);
        } catch (RuntimeException e) {
            return false;
        }
    }

    private int byteOrder;
    private int format;
    private int osVersion;
    private ClassID classID;
    private final List<Section> sections = new ArrayList<>();

    private void init(final byte[] src, final int offset, final int length)
            throws UnsupportedEncodingException {
        /* FIXME (3): Ensure that at most "length" bytes are read. */

        /*
         * Read the stream's header fields.
         */
        int o = offset;
        byteOrder = LittleEndian.getUShort(src, o);
        o += LittleEndianConsts.SHORT_SIZE;
        format = LittleEndian.getUShort(src, o);
        o += LittleEndianConsts.SHORT_SIZE;
        osVersion = (int) LittleEndian.getUInt(src, o);
        o += LittleEndianConsts.INT_SIZE;
        classID = new ClassID(src, o);
        o += ClassID.LENGTH;
        final int sectionCount = LittleEndian.getInt(src, o);
        o += LittleEndianConsts.INT_SIZE;
        if (sectionCount < 0) {
            throw new HPSFRuntimeException("Section count " + sectionCount + " is negative.");
        }

        /*
         * Read the sections, which are following the header. They
         * start with an array of section descriptions. Each one
         * consists of a format ID telling what the section contains
         * and an offset telling how many bytes from the start of the
         * stream the section begins.
         *
         * Most property sets have only one section. The Document
         * Summary Information stream has 2. Everything else is a rare
         * exception and is no longer fostered by Microsoft.
         */

        /*
         * Loop over the section descriptor array. Each descriptor
         * consists of a ClassID and a DWord, and we have to increment
         * "offset" accordingly.
         */
        for (int i = 0; i < sectionCount; i++) {
            final Section s = new Section(src, o);
            o += ClassID.LENGTH + LittleEndianConsts.INT_SIZE;
            sections.add(s);
        }
    }


    public HwpPropertySet() {
        /* Initialize the "byteOrder" field. */
        byteOrder = BYTE_ORDER_ASSERTION;

        /* Initialize the "format" field. */
        format = FORMAT_ASSERTION;

        /* Initialize "osVersion" field as if the property has been created on
         * a Win32 platform, whether this is the case or not. */
        osVersion = (OS_WIN32 << 16) | 0x0A04;

        /* Initialize the "classID" field. */
        classID = new ClassID();

        /* Initialize the sections. Since property set must have at least
         * one section it is added right here. */
        addSection(new Section());
    }

    public HwpPropertySet(HwpPropertySet ps) {
        setByteOrder(ps.getByteOrder());
        setFormat(ps.getFormat());
        setOSVersion(ps.getOSVersion());
        setClassID(ps.getClassID());
        for (final Section section : ps.getSections()) {
            sections.add(new Section(section));
        }
    }

    public HwpPropertySet(final InputStream stream)
            throws NoPropertySetStreamException, IOException {
        if (!isPropertySetStream(stream)) {
            throw new NoPropertySetStreamException();
        }

        final byte[] buffer = IOUtils.toByteArray(stream);
        init(buffer, 0, buffer.length);
    }

    public int getByteOrder() {
        return byteOrder;
    }

    /**
     * Returns the property set stream's low-level "byte order" field.
     *
     * @param byteOrder The property set stream's low-level "byte order" field.
     */
    @SuppressWarnings("WeakerAccess")
    public void setByteOrder(int byteOrder) {
        this.byteOrder = byteOrder;
    }

    /**
     * @return The property set stream's low-level "format" field. It is always {@code 0x0000}.
     */
    public int getFormat() {
        return format;
    }

    /**
     * Sets the property set stream's low-level "format" field.
     *
     * @param format The property set stream's low-level "format" field.
     */
    public void setFormat(int format) {
        this.format = format;
    }

    /**
     * @return The property set stream's low-level "OS version" field.
     */
    public int getOSVersion() {
        return osVersion;
    }

    /**
     * Sets the property set stream's low-level "OS version" field.
     *
     * @param osVersion The property set stream's low-level "OS version" field.
     */
    @SuppressWarnings("WeakerAccess")
    public void setOSVersion(int osVersion) {
        this.osVersion = osVersion;
    }


    /**
     * @return The property set stream's low-level "class ID" field.
     */
    public ClassID getClassID() {
        return classID;
    }

    /**
     * Sets the property set stream's low-level "class ID" field.
     *
     * @param classID The property set stream's low-level "class ID" field.
     */
    @SuppressWarnings("WeakerAccess")
    public void setClassID(ClassID classID) {
        this.classID = classID;
    }

    /**
     * @return The number of {@link Section}s in the property set.
     */
    public int getSectionCount() {
        return sections.size();
    }

    /**
     * @return The unmodifiable list of {@link Section}s in the property set.
     */
    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    public PropertyIDMap getPropertySetIDMap() {
        return null;
    }


    /**
     * Adds a section to this property set.
     *
     * @param section The {@link Section} to add. It will be appended
     * after any sections that are already present in the property set
     * and thus become the last section.
     */
    public void addSection(final Section section) {
        sections.add(section);
    }

    /**
     * Removes all sections from this property set.
     */
    public void clearSections() {
        sections.clear();
    }


    public boolean isSummaryInformation() {
        return !this.getSections().isEmpty()
                && DOCUMENT_SUMMARY_INFORMATION_ID_STR.equals(sections.get(0).getFormatID().toString());
                // && matchesSummary(getFirstSection().getFormatID(), HwpSummaryInformation.FORMAT_ID);
    }

    public boolean isDocumentSummaryInformation() {
        return !this.getSections().isEmpty()
                && Util.equal(this.getFirstSection().getFormatID().getBytes(), SectionIDMap.DOCUMENT_SUMMARY_INFORMATION_ID[0]);
    }




    public Property[] getProperties() throws NoSingleSectionException {
        return getFirstSection().getProperties();
    }



    /**
     * Convenience method returning the value of the property with the specified ID.
     * If the property is not available, {@code null} is returned and a subsequent
     * call to {@link #wasNull} will return {@code true}.
     *
     * @param id The property ID
     * @return The property value
     * @throws NoSingleSectionException if the PropertySet has
     * more or less than one {@link Section}.
     */
    protected Object getProperty(final int id) throws NoSingleSectionException {
        return getFirstSection().getProperty(id);
    }

    public void write(final OutputStream out) throws IOException, WritingNotSupportedException {

        out.write(toBytes());

        /* Indicate that we're done */
        out.close();
    }

    private byte[] toBytes() throws WritingNotSupportedException, IOException {
        try (UnsynchronizedByteArrayOutputStream bos = UnsynchronizedByteArrayOutputStream.builder().get();
             LittleEndianOutputStream leos = new LittleEndianOutputStream(bos)) {

            /* Write the number of sections in this property set stream. */
            final int nrSections = getSectionCount();

            /* Write the property set's header. */
            leos.writeShort(getByteOrder());
            leos.writeShort(getFormat());
            leos.writeInt(getOSVersion());
            putClassId(bos, getClassID());
            leos.writeInt(nrSections);

            assert (bos.size() == OFFSET_HEADER);

            final int[][] offsets = new int[getSectionCount()][2];

            /* Write the section list, i.e. the references to the sections. Each
             * entry in the section list consist of the section's class ID and the
             * section's offset relative to the beginning of the stream. */
            int secCnt = 0;
            for (final Section section : getSections()) {
                final ClassID formatID = section.getFormatID();
                if (formatID == null) {
                    throw new NoFormatIDException();
                }
                putClassId(bos, formatID);
                offsets[secCnt++][0] = bos.size();
                // offset dummy - filled later
                leos.writeInt(-1);
            }

            /* Write the sections themselves. */
            secCnt = 0;
            for (final Section section : getSections()) {
                offsets[secCnt++][1] = bos.size();
                section.write(bos);
            }

            byte[] result = bos.toByteArray();
            for (int[] off : offsets) {
                LittleEndian.putInt(result, off[0], off[1]);
            }

            return result;
        }
    }

    /**
     * Writes a property set to a document in a POI filesystem directory.
     *
     * @param dir The directory in the POI filesystem to write the document to.
     * @param name The document's name. If there is already a document with the
     * same name in the directory the latter will be overwritten.
     *
     * @throws WritingNotSupportedException if the filesystem doesn't support writing
     * @throws IOException if the old entry can't be deleted or the new entry be written
     */
    public void write(final DirectoryEntry dir, final String name)
            throws WritingNotSupportedException, IOException {
        /* If there is already an entry with the same name, remove it. */
        if (dir.hasEntryCaseInsensitive(name)) {
            final Entry e = dir.getEntryCaseInsensitive(name);
            e.delete();
        }

        /* Create the new entry. */
        dir.createDocument(name, toInputStream());
    }

    public InputStream toInputStream() throws WritingNotSupportedException, IOException {
        return UnsynchronizedByteArrayInputStream.builder().setByteArray(toBytes()).get();
    }

    String getPropertyStringValue(final int propertyId) {
        Object propertyValue = getProperty(propertyId);
        return getPropertyStringValue(propertyValue);
    }

    public static String getPropertyStringValue(final Object propertyValue) {
        // Normal cases
        if (propertyValue == null) {
            return null;
        }
        if (propertyValue instanceof String) {
            return (String)propertyValue;
        }

        // Do our best with some edge cases
        if (propertyValue instanceof byte[]) {
            byte[] b = (byte[])propertyValue;
            switch (b.length) {
                case 0:
                    return "";
                case 1:
                    return Byte.toString(b[0]);
                case 2:
                    return Integer.toString( LittleEndian.getUShort(b) );
                case 4:
                    return Long.toString( LittleEndian.getUInt(b) );
                default:
                    // Maybe it's a string? who knows!
                    try {
                        return CodePageUtil.getStringFromCodePage(b, Property.DEFAULT_CODEPAGE);
                    } catch (UnsupportedEncodingException e) {
                        // doesn't happen ...
                        return "";
                    }
            }
        }
        return propertyValue.toString();
    }


    boolean getPropertyBooleanValue(final int id) throws NoSingleSectionException {
        // poi.hpsf.Section 에서 가져옴
        final Boolean b = (Boolean) getFirstSection().getProperty(id);
        return b != null && b;

    }

    int getPropertyIntValue(final int id) throws NoSingleSectionException {
        // poi.hpsf.Section 에서 가져옴
        final Number i;
        final Object o = getFirstSection().getProperty(id);
        if (o == null) {
            return 0;
        }
        if (!(o instanceof Long || o instanceof Integer)) {
            throw new HPSFRuntimeException
                    ("This property is not an integer type, but " +
                            o.getClass().getName() + ".");
        }
        i = (Number) o;
        return i.intValue();
    }

    void remove1stProperty(long id) {
        getFirstSection().removeProperty(id);
    }

    void set1stProperty(long id, String value) {
        getFirstSection().setProperty((int)id, value);
    }

    void set1stProperty(long id, int value) {
        getFirstSection().setProperty((int)id, value);
    }

    void set1stProperty(long id, boolean value) {
        getFirstSection().setProperty((int)id, value);
    }

//    @SuppressWarnings("SameParameterValue")
    void set1stProperty(long id, byte[] value) {
        getFirstSection().setProperty((int)id, value);
    }

    public boolean wasNull() throws NoSingleSectionException {
        return getFirstSection().wasNull();
    }



    /**
     * Gets the PropertySets first section.
     *
     * @return The PropertySets first section.
     */
    @SuppressWarnings("WeakerAccess")
    public Section getFirstSection() {
        if (sections.isEmpty()) {
            throw new MissingSectionException("Property set does not contain any sections.");
        }
        return sections.get(0);
    }


    /**
     * Returns {@code true} if the {@code PropertySet} is equal
     * to the specified parameter, else {@code false}.
     *
     * @param o the object to compare this {@code PropertySet} with
     *
     * @return {@code true} if the objects are equal, {@code false}
     * if not
     */
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PropertySet)) {
            return false;
        }
        final PropertySet ps = (PropertySet) o;
        int byteOrder1 = ps.getByteOrder();
        int byteOrder2 = getByteOrder();
        ClassID classID1 = ps.getClassID();
        ClassID classID2 = getClassID();
        int format1 = ps.getFormat();
        int format2 = getFormat();
        int osVersion1 = ps.getOSVersion();
        int osVersion2 = getOSVersion();
        int sectionCount1 = ps.getSectionCount();
        int sectionCount2 = getSectionCount();
        if (byteOrder1 != byteOrder2      ||
                !classID1.equals(classID2)    ||
                format1 != format2            ||
                osVersion1 != osVersion2      ||
                sectionCount1 != sectionCount2) {
            return false;
        }

        /* Compare the sections: */
        return getSections().containsAll(ps.getSections());
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();
        final int sectionCount = getSectionCount();
        b.append(getClass().getName());
        b.append('[');
        b.append("byteOrder: ");
        b.append(getByteOrder());
        b.append(", classID: ");
        b.append(getClassID());
        b.append(", format: ");
        b.append(getFormat());
        b.append(", OSVersion: ");
        b.append(getOSVersion());
        b.append(", sectionCount: ");
        b.append(sectionCount);
        b.append(", sections: [\n");
        for (Section section: getSections()) {
            b.append(section.toString(getPropertySetIDMap()));
        }
        b.append(']');
        b.append(']');
        return b.toString();
    }

    private static void putClassId(final UnsynchronizedByteArrayOutputStream out, final ClassID n) {
        byte[] b = new byte[16];
        n.write(b, 0);
        out.write(b, 0, b.length);
    }
}
