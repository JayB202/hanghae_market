
# cleon_당근마켓



>개발 기간: 2023.05.12 ~ 2023.05.18</p>🎥 시연영상 

### 🖐당근마켓 클론
  - 당근 마켓을 클
## 📃 S.A
[https://www.notion.so/S-A-afa6ceba07db4022bebde44ccb35807a](https://www.notion.so/2-SA-168bab4b54f34ba68ef48055677f888e)

## 📜와이어 프레임
[<img width="913" alt="스크린샷 2023-05-11 014246" src="https://github.com/seunghee58/miniproject_hanhae66/assets/129656095/b30b6beb-c5e0-4635-b1ab-7bc7847a0d3a">
](https://www.figma.com/file/gzzljAA589QkfmtcSsKPXs/HangHaeMarket?type=design&node-id=0-1)



## 📖 API 명세서
<details>
  <summary> 펼쳐보기 </summary>
<img width="964" alt="hh66 API 명세서" src="https://github.com/seunghee58/miniproject_hanhae66/assets/129656095/719cd245-0070-4e8d-9870-8fb55d2f5265">
</details>

## 👨‍👩‍👧팀원
|이름|역할|
|------|---|
|김종범(BE팀장)</br>[@JayB202](https://github.com/JayB202)|
|강동현</br>[@kangdh208](https://github.com/kangdh208)|
|정종현</br>[@Shuan75](https://github.com/Shuan75)|
|김영기</br>[@youngkikim14](https://github.com/youngkikim14)|

FE git hub : 

## ⚙️ Tech Stack
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <br>
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <br>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> <br>
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

## 1️⃣ 회원 관련 기능
  1. 회원 가입 API <br>
    - userId : 크기 4 이상, 10 이하 / 소문자와 숫자만 입력가능, 공백 금지 / **중복 불가** <br>
    - userName : 크기 10이하, 공백 금지 <br>
    - password : 크기 8 이상, 15 이하 / 대소문자, 숫자, 특수문자 가능, 공백 금지 <br>
    
  2. 로그인 / 로그아웃 API<br>
    - 로그인 성공 시, Access Token과 Refresh Token을 발급하고 헤더에 토큰을 추가<br>

  3. 마이페이지 API <br>
    - 나의 정보 확인 <br>
    - 내가 쓴 게시글, 댓글 목록 조회 <br>
    - 내가 관심 표시한 게시글 <br>
  
## 2️⃣ 게시글 관련 기능
  1. 게시글 작성 API <br>
    - 카테코리를 선택 후 글을 작성 <br>
  
  2. 게시글 수정 API <br>
    - 내가 작성한 글에 한하여 수정 가능 <br>
    
  3. 게시글 삭제 API <br>
    - 내가 작성한 글에 한하여 삭제 가능 <br>

  4. 게시글 목록 조회 API <br>
    - 게시글 목록을 내림차순으로 조회 <br>
    - 카테고리 별 게시글 조회 <br>
    
  5. 게시글 상세 조회 API <br>
    - 게시글의 내용과 댓글 리스트 
    
  6. 게시글 좋아요 API <br>
    - 게시글에 좋아요 버튼을 눌러 좋아요 표시 <br>
    - 버튼을 한 번 더 누르면 취소 <br>
  
  7. 게시글 검색 API <br>
    - 키워드를 입력하여 **제목+내용, 제목, 내용**으로 게시글 조회 <br>
  
  8. 댓글 작성 API <br>
    - 모든 게시글에 댓글 작성 가능 <br>

  9. 댓글 좋아요 API <br>
    - 댓글에 좋아요 버튼을 눌러 좋아요 표시 가능 <br>
    - 버튼을 한 번 더 누르면 취소 <br>

## 🚩 기능 구현을 위해 고민한 것
