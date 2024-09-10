# .env 파일이 존재하는지 확인
if [ -f .env ]; then
    # .env 파일에서 변수 로드
    export $(grep -v '^#' .env | xargs)
    echo ".env 파일에서 환경 변수를 로드했습니다."
else
    echo ".env 파일을 찾을 수 없습니다."
    exit 1
fi

# Gradle 빌드 실행
echo "Gradle 빌드를 시작합니다..."
./gradlew clean build shadowJar

# 빌드 결과 확인
if [ $? -eq 0 ]; then
    echo "빌드가 성공적으로 완료되었습니다."
else
    echo "빌드 중 오류가 발생했습니다."
    exit 1
fi
