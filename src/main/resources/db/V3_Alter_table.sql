-- 현업에서는 hard delete보다 soft delete 하여 관리합니다
-- device테이블은 지워도 지장없다고 생각하여 포함시키지 않았습니다

ALTER TABLE point ADD deleted boolean default false;
ALTER TABLE brand ADD deleted boolean default false;
ALTER TABLE category ADD deleted boolean default false;
ALTER TABLE orders ADD deleted boolean default false;
ALTER TABLE product ADD deleted boolean default false;
ALTER TABLE review ADD deleted boolean default false;
ALTER TABLE user ADD deleted boolean default false;