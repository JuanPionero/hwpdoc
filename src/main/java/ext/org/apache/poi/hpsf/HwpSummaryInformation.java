/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package ext.org.apache.poi.hpsf;

import org.apache.poi.hpsf.*;
import org.apache.poi.hpsf.wellknown.PropertyIDMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static ext.org.apache.poi.hhwpf.Specification.DEFAULT_AUTHOR_FOR_SUMMARY;
import static ext.org.apache.poi.hhwpf.Specification.DOCUMENT_SUMMARY_INFORMATION_ID_STR;

/**
 * <p>Apache POI를 활용, 한컴 hwp 포맷의 Summary Information stream in a
 * HWP document.</p>
 *
 * @author Seung Hoon Lee <a
 * href="mailto:juanlee0@naver.com">&lt;juanlee0@naver.com&gt;</a>
 * @see DocumentSummaryInformation
 */
public final class HwpSummaryInformation extends HwpPropertySet {

    public static final ClassID FORMAT_ID = new ClassID(DOCUMENT_SUMMARY_INFORMATION_ID_STR);

    /**
     * <p>{@link SummaryInformation} 를 닮은 HwpSummaryInformation 생성 .</p>
     *
     * @param ps A property set which should be created from a summary
     *           information stream.
     * @throws UnexpectedPropertySetTypeException if <var>ps</var> does not
     *                                            contain a summary information stream.
     */
    public HwpSummaryInformation(final HwpPropertySet ps)
            throws UnexpectedPropertySetTypeException {
        super(ps);
        if (!isSummaryInformation())
            throw new UnexpectedPropertySetTypeException("Not a "
                    + getClass().getName());
    }

    public HwpSummaryInformation(final InputStream stream)
            throws NoPropertySetStreamException, IOException {
        super(stream);
    }

    @Override
    public PropertyIDMap getPropertySetIDMap() {
        return PropertyIDMap.getSummaryInformationProperties();
    }

    public String getTitle() {
        return getPropertyStringValue(PropertyIDMap.PID_TITLE);
    }

    public void setTitle(final String title) {
        set1stProperty(PropertyIDMap.PID_TITLE, title);
    }

    public void removeTitle() {
        remove1stProperty(PropertyIDMap.PID_TITLE);
    }

    public String getSubject() {
        return getPropertyStringValue(PropertyIDMap.PID_SUBJECT);
    }

    public void setSubject(final String subject) {
        set1stProperty(PropertyIDMap.PID_SUBJECT, subject);
    }

    public void removeSubject() {
        remove1stProperty(PropertyIDMap.PID_SUBJECT);
    }

    public String getAuthor() {
        return getPropertyStringValue(PropertyIDMap.PID_AUTHOR);
    }

    public void setAuthor(final String author) {
        set1stProperty(PropertyIDMap.PID_AUTHOR, author);
    }

    public void removeAuthor() {
        remove1stProperty(PropertyIDMap.PID_AUTHOR);
    }

    public String getKeywords() {
        return getPropertyStringValue(PropertyIDMap.PID_KEYWORDS);
    }

    public void setKeywords(final String keywords) {
        set1stProperty(PropertyIDMap.PID_KEYWORDS, keywords);
    }

    public void removeKeywords() {
        remove1stProperty(PropertyIDMap.PID_KEYWORDS);
    }

    public String getComments() {
        return getPropertyStringValue(PropertyIDMap.PID_COMMENTS);
    }

    public void setComments(final String comments) {
        set1stProperty(PropertyIDMap.PID_COMMENTS, comments);
    }

    public void removeComments() {
        remove1stProperty(PropertyIDMap.PID_COMMENTS);
    }


    public String getTemplate() {
        return getPropertyStringValue(PropertyIDMap.PID_TEMPLATE);
    }

    public void setTemplate(final String template) {
        set1stProperty(PropertyIDMap.PID_TEMPLATE, template);
    }

    public void removeTemplate() {
        remove1stProperty(PropertyIDMap.PID_TEMPLATE);
    }

    public String getLastAuthor() {
        return getPropertyStringValue(PropertyIDMap.PID_LASTAUTHOR);
    }

    public void setLastAuthor(final String lastAuthor) {
        set1stProperty(PropertyIDMap.PID_LASTAUTHOR, lastAuthor);
    }

    public void removeLastAuthor() {
        remove1stProperty(PropertyIDMap.PID_LASTAUTHOR);
    }

    public String getRevNumber() {
        return getPropertyStringValue(PropertyIDMap.PID_REVNUMBER);
    }

    public void setRevNumber(final String revNumber) {
        set1stProperty(PropertyIDMap.PID_REVNUMBER, revNumber);
    }

    public void removeRevNumber() {
        remove1stProperty(PropertyIDMap.PID_REVNUMBER);
    }

    public long getEditTime() {
        final Date d = (Date) getProperty(PropertyIDMap.PID_EDITTIME);
        if (d == null) {
            return 0;
        }
        return Util.dateToFileTime(d);
    }

    public void setEditTime(final long time) {
        final Date d = Util.filetimeToDate(time);
        getFirstSection().setProperty(PropertyIDMap.PID_EDITTIME, Variant.VT_FILETIME, d);
    }

    public void removeEditTime() {
        remove1stProperty(PropertyIDMap.PID_EDITTIME);
    }

    public Date getLastPrinted() {
        return (Date) getProperty(PropertyIDMap.PID_LASTPRINTED);
    }

    public void setLastPrinted(final Date lastPrinted) {
        getFirstSection().setProperty(PropertyIDMap.PID_LASTPRINTED, Variant.VT_FILETIME,
                lastPrinted);
    }

    public void removeLastPrinted() {
        remove1stProperty(PropertyIDMap.PID_LASTPRINTED);
    }

    public Date getCreateDateTime() {
        return (Date) getProperty(PropertyIDMap.PID_CREATE_DTM);
    }

    public void setCreateDateTime(final Date createDateTime) {
        getFirstSection().setProperty(PropertyIDMap.PID_CREATE_DTM, Variant.VT_FILETIME,
                createDateTime);
    }


    public void removeCreateDateTime() {
        remove1stProperty(PropertyIDMap.PID_CREATE_DTM);
    }


    public Date getLastSaveDateTime() {
        return (Date) getProperty(PropertyIDMap.PID_LASTSAVE_DTM);
    }


    public void setLastSaveDateTime(final Date time) {
        getFirstSection().setProperty(PropertyIDMap.PID_LASTSAVE_DTM,
                Variant.VT_FILETIME, time);
    }


    public void removeLastSaveDateTime() {
        remove1stProperty(PropertyIDMap.PID_LASTSAVE_DTM);
    }

    public int getPageCount() {
        return getPropertyIntValue(PropertyIDMap.PID_PAGECOUNT);
    }

    public void setPageCount(final int pageCount) {
        set1stProperty(PropertyIDMap.PID_PAGECOUNT, pageCount);
    }


    public void removePageCount() {
        remove1stProperty(PropertyIDMap.PID_PAGECOUNT);
    }

    public int getWordCount() {
        return getPropertyIntValue(PropertyIDMap.PID_WORDCOUNT);
    }

    public void setWordCount(final int wordCount) {
        set1stProperty(PropertyIDMap.PID_WORDCOUNT, wordCount);
    }

    public void removeWordCount() {
        remove1stProperty(PropertyIDMap.PID_WORDCOUNT);
    }

    public int getCharCount() {
        return getPropertyIntValue(PropertyIDMap.PID_CHARCOUNT);
    }

    public void setCharCount(final int charCount) {
        set1stProperty(PropertyIDMap.PID_CHARCOUNT, charCount);
    }

    public void removeCharCount() {
        remove1stProperty(PropertyIDMap.PID_CHARCOUNT);
    }

    public byte[] getThumbnail() {
        return (byte[]) getProperty(PropertyIDMap.PID_THUMBNAIL);
    }

    public void setThumbnail(final byte[] thumbnail) {
        getFirstSection().setProperty(PropertyIDMap.PID_THUMBNAIL, /* FIXME: */
                Variant.VT_LPSTR, thumbnail);
    }

    public void removeThumbnail() {
        remove1stProperty(PropertyIDMap.PID_THUMBNAIL);
    }

    public String getApplicationName() {
        return getPropertyStringValue(PropertyIDMap.PID_APPNAME);
    }

    public void setApplicationName(final String applicationName) {
        set1stProperty(PropertyIDMap.PID_APPNAME, applicationName);
    }

    public void removeApplicationName() {
        remove1stProperty(PropertyIDMap.PID_APPNAME);
    }

    public int getSecurity() {
        return getPropertyIntValue(PropertyIDMap.PID_SECURITY);
    }

    public void setSecurity(final int security) {
        set1stProperty(PropertyIDMap.PID_SECURITY, security);
    }


    public void removeSecurity() {
        remove1stProperty(PropertyIDMap.PID_SECURITY);
    }

}
