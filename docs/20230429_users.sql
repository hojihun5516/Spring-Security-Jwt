create table spring_security.users
(
    id                          bigint        not null auto_increment primary key,
    username                    varchar(100)  not null                                  comment '유저 아이디',
    password                    varchar(100)      null                                  comment '비밀번호',
    name                        varchar(20)   not null                                  comment '이름',
    birthday                    date              null                                  comment '생일',
    deleted_at                  datetime          null                                  comment '탈퇴 일시',
    created_at                  datetime      not null default CURRENT_TIMESTAMP        comment '생성 일시',
    updated_at                  datetime      not null default CURRENT_TIMESTAMP        comment '업데이트 일시'
) comment ='유저 기본 정보';
