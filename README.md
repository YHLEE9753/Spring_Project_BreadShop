## Yong Hoon Bread Shop - Spring Project

Project : Gradle<br>
Language : Java<br>
Spring Boot Version : 2.6.7<br>
Java Version : 17<br>
Packaging : Jar<br>
DB : MYSQL<br>
Test : junit5 + TestContainers<br>

## 기능
### Product 관리자 기능
Product CRUD 기능<br>
Product 판매상태 변경을 통해 판매가능 유무 설정<br>
Product 세일상태 변경을 통해 세일 유무 및 세일 가격 설정

### Order
Order CRUD 기능<br>
Order 와 email, address, postcode 작성 후 submit 시 주문서 발급<br>

### Rest
Rest API 작성 및 전송 기능


### 겪었던 어려움과 문제
1. 타임리프 사용에서 어려움

Ajax 사용에 어려움을 겪어서 타임리프로 프론트 단을 작성하고 restApi 를 통해 json 을 받는 모습을 보여주는 방식으로 이용하였습니다.
타임리프사용이 미숙하여 매직넘버 를 사용하는 코드가 있어 깔끔한 코드 작성이 어려웠습니다.
프론트에서 데이터를 정리하여 보내주는 방식을 사용한다면 훨씬 깔끔하게 데이터 송수신이 가능할거로 보입니다.



2. 테스트 코드 작성에서의 어려움

Controller 테스트 코드 작성에서 통합테스트를 진행하면서 mock 객체를 사용할려고 시도하였습니다<br>

-> 멘토와의 면담을 통해 통합 테스트의 경우 testContainer 를 통한 테스트 진행이 맞으며 Service 단에서의 테스트가 진행이 완료되면 Controller 에서는 단위테스트(url 연결) 만 진행해도 충분하다고 하셨습니다.



3. 발생 가능한 모든 예외 처리

사전에 시나리오에 대한 작성을 하지 않아 예상치 못한 예외 처리 발생을 겪었습니다. 그때 그때 수정하기 보다는 먼저 시나리오를 작성함으로써 사전에 에러를 방지해야 될것 같습니다.






