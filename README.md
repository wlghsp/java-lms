# 학습 관리 시스템(Learning Management System)
## 진행 방법
* 학습 관리 시스템의 수강신청 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 리펙토링 작업사항
* [ ] Question의 Answer리스트를 일급 컬렉션으로 변경
* [ ] deleteQuestion 메서드 변경
  * [ ] delete작업 및 히스토리를 도메인에서 처리하도록 변경
  * [ ] 라인 수 줄이기
* [ ] DeleteHistory 생성시 인스턴스변수 줄이기 

## 질문 삭제하기 요구사항
* 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
* 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
* 답변이 없는 경우 삭제가 가능하다.
* 질문자와 답변글의 모든답변자 같은경우 삭제가 가능하다.
* 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다.
* 질문자와 답변자가 다른경우 답변을 삭제 할 수 없다.
* 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.
## 리팩터링 요구사항
* nextstep.qna.service.QnaService의 deleteQuestion()는 앞의 질문 삭제 기능을 구현한 코드이다. 이 메소드는 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드가 섞여 있다.
* QnaService의 deleteQuestion() 메서드에 단위 테스트 가능한 코드(핵심 비지니스 로직)를 도메인 모델 객체에 구현한다.
* QnaService의 비지니스 로직을 도메인 모델로 이동하는 리팩터링을 진행할 때 TDD로 구현한다.
* QnaService의 deleteQuestion() 메서드에 대한 단위 테스트는 src/test/java 폴더 nextstep.qna.service.QnaServiceTest이다. 도메인 모델로 로직을 이동한 후에도 QnaServiceTest의 모든 테스트는 통과해야 한다.

## 경험해야할 학습 목표
* 레거시 코드를 리팩터링할 때 테스트 코드를 통해 보호하는 경험
* Q&A 서비스의 질문 삭제하기 기능의 레거시 코드를 리팩터링하는 경험
* DB 테이블보다 도메인 모델을 TDD 기반으로 먼저 개발해 보는 경험
* 레거시 코드를 점진적으로 리팩터링해보는 경험
* 현장과 유사한 기능 요구사항을 지금까지 학습한 TDD, OOP, 클린 코드 기반으로 개발해 보는 경험

## 객체지향 생활 체조 원칙
* 규칙 1: 한 메서드에 오직 한 단계의 들여쓰기만 한다.
* 규칙 2: else 예약어를 쓰지 않는다.
* 규칙 3: 모든 원시값과 문자열을 포장한다.
* 규칙 4: 한 줄에 점을 하나만 찍는다.
* 규칙 5: 줄여쓰지 않는다(축약 금지).
* 규칙 6: 모든 엔티티를 작게 유지한다.
* 규칙 7: 3개 이상의 인스턴스 변수를 가진 클래스를 쓰지 않는다.
* 규칙 8: 일급 콜렉션을 쓴다.
* 규칙 9: 게터/세터/프로퍼티를 쓰지 않는다.