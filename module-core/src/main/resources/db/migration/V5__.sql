-- is_app_installed 컬럼을 is_registered로 변경
ALTER TABLE odiya.member
    RENAME COLUMN is_app_installed TO is_registered;

-- is_registered 컬럼의 데이터 타입을 BOOLEAN으로 변경 (이미 BOOLEAN이라면 이 단계는 생략 가능)
ALTER TABLE odiya.member
    ALTER COLUMN is_registered TYPE BOOLEAN USING is_registered::BOOLEAN;

-- is_registered 컬럼에 NOT NULL 제약 추가 (이미 NOT NULL이라면 이 단계는 생략 가능)
ALTER TABLE odiya.member
    ALTER COLUMN is_registered SET NOT NULL;

-- 인덱스가 이미 존재하는지 확인 후 생성
DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_indexes
            WHERE schemaname = 'odiya'
              AND tablename = 'member'
              AND indexname = 'idx_member_phone_number'
        ) THEN
            CREATE INDEX idx_member_phone_number ON odiya.member (phone_number);
        END IF;
    END $$;
