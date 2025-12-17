
# hwpdoc

## 개요
아래한글 또는 아래아한글이라고 1990년대 나온 편집프로그램. 많은 편집 작업을 했던 기억이 아직도 생생하다.  
수십년이 지난 지금, 한글문서 생성 자동화 작업이 필요해서 apache.poi와 유사한 라이브러리를 찾다가 우연히 [hwplib]를 
발견하고 분석을 시작했다.  
사용하기에 앞서 [hwplib]에서 내장하여 사용중인 apache.poi에 대한 의존도를 줄이고 새로운 java 버전의 코드
적용을 위해 [neolord0]님의 기존 코드를 유지하지 못하고 멀어지게 되었으나 원작자님의 정신은 그대로 유지하고자 합니다.  

## hwplib와 다른점
- 내장된 apache.poi 코드를 3rd party 라이브러리로 운용. 필수적인 일부 코드만 내장시킴.
- "Instance 생성 후 구성" 방식 외에 "구성 데이터로 Instance 생성" 방식 추가. 후자 위주로 소스코드 재구성.
- 읽기, 쓰기를 위한 별도 클래스 제거. HHWPFDocument의 메소드 및 constructor로 처리.
- hwplib에서 제공하는 여러 샘플코드는 수정 필요. 

## hwplib와 같은점 
- 한글과컴퓨터에서 공개한  '한글 문서 파일 구조 5.0' 문서 ( http://www.hancom.com/etc/hwpDownload.do?gnb0=269&gnb1=271&gnb0=101&gnb1=140 ) <br>
  ( “본 제품은 한글과컴퓨터의 HWP 문서 파일(.hwp) 공개 문서를 참고하여 개발하였습니다." )
- Apache-POI 라이브러리의 사용 : 방법은 다르지만 동일 라이브러리 사용. 
- 지원 안하는 기능
  - 암호화된 HWP 파일에 대한 읽고 쓰기
  - 이미지 파일, PDF 파일, HTML 파일로 변환
  - 렌더링 후 연산이 필요한 문서 속성 가져오기. 예: 전체 페이지 수.

ㅑ



한글과 컴퓨터(한컴)에서 만든 워드프로세서 "한글"의 파일에 대한 라이브러리입니다.<br>

개인적인 취미 생활 또는 사회기여 활동 목적으로 시작한 hwplib, hwpxlib 프로젝트가 이 라이브러리를 기반으로 하는 상용 제품이 개발하여 판매하고 있습니다.
이 라이브러리의 저작권은 저 개인에게 있으므로, 라이브러리 사용, 버그 수정요청, 약간의 질문 등은 Apache-2.0 license에 의해 앞으로도 자유롭게 할 수 있습니다.
그 외에 많은 시간을 초래할 수 있는 기술지원 요청이나 유지보수 계약등은 제 메일로 상의해 주셨으면 합니다. <br>


* 메이븐 레파지토리 설정
    ```{.xml}
    <dependency>
        <groupId>kr.dogfoot</groupId>
        <artifactId>hwplib</artifactId>
    </dependency>
    ```
 - 1.1.5 버전 : CtrlHeaderGso.copy() 에러남 

* hwpx 파일에 대한 라이브러리는 https://github.com/neolord0/hwpxlib 을 참조해 주세요.
* hwp파일을 hwpx파일로 변환하는 라이브러리는 https://github.com/neolord0/hwp2hwpx 을 참조해 주세요.

## References
[neolord0]: https://github.com/neolord0
[hwplib]:  https://github.com/neolord0/hwplib
[hwpx]:  https://github.com/neolord0/hwpxlib 
