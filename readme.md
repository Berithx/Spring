# Todo 관리 웹 서버
해당 프로그램은 Todo 관리 웹 서버입니다. <br>

탑재된 기능은 Todo 등록, 조회, 수정, 삭제입니다.

각 기능은 RESTful API를 통해 구현되었으며, Client는 HTTP 요청을 통해 서버와 상호작용합니다.

이 서버는 IntelliJ를 사용하여 개발되었으며, 데이터베이스는 MySQL를 사용합니다.

<br>
## ■ Tech
- **IDE** : IntelliJ IDEA
- **언어** : JAVA
- **버전** : JDK 17
- **DB** : MySQL 8.0
- **Framework** : SpirngBoot 3.1.0

<details>
<summary>입문 주차 ver.1</summary>
## ■ Feature
1. **Todo 등록**
    - 사용자는 제목, 내용, 담당자, 비밀번호를 입력하여 Todo를 등록할 수 있습니다.
    - 생성된 Todo 정보는 비밀번호를 제외하고 반환되어, 정상 등록 처리된 것을 확인할 수 있습니다.
2. **Todo 조회**
   - 사용자는 특정 Todo를 선택하여 상세 내용을 조회할 수 있습니다.
   - 사용자는 전체 Todo 목록을 조회할 수 있습니다. 조회 형태는 작성일을 기준으로 내림차순으로 정렬되어 최근 등록 내역부터 확인 할 수 있습니다.
3. **Todo 수정**
    - 사용자는 특정 Todo의 제목, 내용, 담당자를 수정할 수 있습니다.
    - 수정 간 비밀번호를 필요로 하며 DB 데이터와 일치한 경우에만 수정을 허용합니다.
4. **Todo 삭제**
    - 사용자는 특정 Todo를 삭제할 수 있습니다.
    - 삭제 간 비밀번호를 필요로 하며 DB 데이터와 일치한 경우에만 삭제를 허용합니다.



## ■ Use Case Diagram
<img src="https://teamsparta.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2Fbc57b41f-4c0b-4714-9d3b-aa12909f0f56%2FUntitled.png?table=block&id=3bc7ad52-0a2a-4cc2-9f30-ec48717b9814&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=960&userId=&cache=v2">

## ■ ERD
<img src="https://teamsparta.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2Feb7a5f14-bc48-42c0-821e-8d2fc3edeaa5%2F%25EC%258A%25A4%25ED%2581%25AC%25EB%25A6%25B0%25EC%2583%25B7_2024-05-17_101208.png?table=block&id=785ee4aa-1584-46a9-bbef-3af43663f6f2&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=930&userId=&cache=v2">

## ■ API 명세서
<img src="https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2Fb79426fd-1e8d-4e04-9d14-1242be1166ae%2F%25EC%258A%25A4%25ED%2581%25AC%25EB%25A6%25B0%25EC%2583%25B7_2024-05-17_112010.png?table=block&id=5b00f43c-10f2-424c-8461-a2f9f4e6010d&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=2000&userId=81832d12-bc15-4ae9-a090-4b1b1ca1bbe6&cache=v2">
</details>
