insert into category(name) values("맨투맨");
insert into category(name) values("후드티");
insert into category(name) values("셔츠");
insert into category(name) values("롱패딩");
--
insert into brand(name) values("노스페이스");
insert into brand(name) values("네셔널지오그래픽");
insert into brand(name) values("아디다스");
insert into brand(name) values("나이키");
insert into brand(name) values("디스커버리");
--
drop table stock_history;
alter table product drop image;
alter table product add stock bigint default 0;
alter table product add origin_image_path longtext;
alter table product add thumbnail_image_path longtext;





