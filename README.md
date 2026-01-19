
# hwpdoc


## 개요
아래한글 또는 아래아한글이라고 1990년대 나온 편집프로그램. 많은 편집 작업을 했던 기억이 아직도 생생하다.  
수십년이 지난 지금, 한글문서 생성 자동화 작업이 필요해서 apache.poi와 유사한 라이브러리를 찾다가 우연히 [hwplib]를 
발견하고 분석을 시작했다.  
사용하기에 앞서 [hwplib]에서 내장하여 사용중인 apache.poi에 대한 연결 강도를 줄이고 새로운 java 버전 방식의 코드 적용을 추구했다. 이로 인해 부득이하게 [neolord0]님의 기존 코딩 스타일에서 멀어지게 되었으나 원작자님의 정신은 그대로 유지하고자 한다. 

## 유의사항
- 새로운 시도의 일환으로 시작한 작업이라 코드 정리, 문서화 작업은 고려하지 않고 있다. 

## hwplib와 다른점
- 내장된 apache.poi 코드를 3rd party 라이브러리로 운용. 필수적인 일부 코드만 내장시킴.
- "Instance 생성 후 구성" 방식 외에 "구성 데이터로 Instance 생성" 방식 추가. 후자 위주로 소스코드 재구성.
- 읽기, 쓰기를 위한 별도 클래스 제거. HHWPFDocument의 메소드 및 constructor로 처리.
- [hwplib]에서 제공하는 여러 샘플코드는 수정 필요. 
- 빌드 도구: gradle

## hwplib와 같은점 
- 한글과컴퓨터에서 공개한  '한글 문서 파일 구조 5.0' 문서 ( http://www.hancom.com/etc/hwpDownload.do?gnb0=269&gnb1=271&gnb0=101&gnb1=140 )   
  ( “본 제품은 한글과컴퓨터의 HWP 문서 파일(.hwp) 공개 문서를 참고하여 개발하였습니다." )
- Apache-POI 라이브러리의 사용 : 방법은 다르지만 동일 라이브러리 사용. 
- 지원 안하는 기능
  - 암호화된 HWP 파일에 대한 읽고 쓰기
  - 이미지 파일, PDF 파일, HTML 파일로 변환
  - 렌더링 후 연산이 필요한 문서 속성 가져오기. 예: 전체 페이지 수.

## 실행환경 
- 컴파일 JDK version: 17 (LTS 버전으로 14 이상에서 가장 많이 사용되는 버전 : https://newrelic.com/resources/report/2024-state-of-the-java-ecosystem)
- JVM version은 14 이상이어야 함: [switch expression](https://openjdk.org/jeps/361)이 사용됨.


## 이슈
### 2025.12.25
- GitHub에 첫 공개 : [hwpdoc] 
### 2026.01.17
- Maven Central Respository에 첫 공개 : [maven-hwpdoc]

## References  
- hwplib 원작자:[박성균](https://github.com/neolord0)
-------
[neolord0]: https://github.com/neolord0
[hwplib]:  https://github.com/neolord0/hwplib
[hwpxlib]:  https://github.com/neolord0/hwpxlib 
[hwpdoc]: https://github.com/JuanPionero/hwpdoc
[maven-hwpdoc]: https://mvnrepository.com/artifact/kr.stocklab/hwpdoc