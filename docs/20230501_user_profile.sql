create table spring_security.user_profiles
(
    id                          bigint        not null auto_increment primary key,
    name                        varchar(20)   not null                                  comment '이름',
    role                        varchar(50)   not null                                  comment '역할',
    user_id                     bigint        not null                                  comment 'FK to user.id',
    deleted_at                  datetime          null                                  comment '탈퇴 일시',
    created_at                  datetime      not null default CURRENT_TIMESTAMP        comment '생성 일시',
    updated_at                  datetime      not null default CURRENT_TIMESTAMP        comment '업데이트 일시'
) comment ='유저 프로필 정보';
