#1/bin/bash

REPOSITORY=/home/ec2-user/app/step1
// 프로젝트 디렉토리 주소는 자주 사용하는 값이니까 변수로 저장
// 쉘에서는 타입 없이 선언하여 저장
// 쉘에서는 $변수명으로 변수를 사용할 수 있습니다.
PROJECT_NAME=stimulus

cd $REPOSITORY/$PROJECT_NAME/
// 제일 처음 git clone 받았던 디렉토리로 이동

echo "> Git Pull"

git pull

echo "> 프로젝트Build 시작"

./gradlew build

echo "> step1 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/
// build의 결과물인 jar파일을 복사해 jar 파일을 모아둔 위치로 복사

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${PROEJCT_NAME}.*.jar)
// 기존에 수행 중이던 스프링부트 애플리케이션을 종료함
// pgrep은 process id만 추출하는 명령어임
// -f 옵션은 프로세스 이름으로 찾는다

echo "> 현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi
// if~else~fi
// 현재 구동 중인 프로세스가 있는지 없는지를 판단해서 기능을 수행
// 프로세스 아이디값을 보고 프로세스가 있으면 해당 프로세스 종료

echo ">새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
// 새로 실행할 jar파일명을 찾는다.
// 여러 jar 파일이 생기기 때묹에 tail -n 로 가장 나중의 jar파일(최신파일)을 변수에 저장

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
// 찾은 jar 파일명으로 해당 jar파일을 nohup으로 실행
// 스프링부트의 장점으로 특별히 외장 톰캣을 설치할 필요 x
// 내장 톰캣을 사용해서 jar 파일만 있으면 바로 웹 애플리케이션 서버를 실행할 수 있다.
// 일반적으로 자바를 실행할 때는 java -jar라는 명령어를 사용하지만, 
// 이렇게 하면 사용자가 터미널 접속을 끊을 때 애플리케이션도 같이 종료된다.
// 애플리케이션 실행자가 터미널을 종료해도 애플리케이션은 계속 구동될 수 있도록 nohup명령어를 사용
