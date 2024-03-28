INSERT INTO users (nickname, email, password) VALUES ('1', 'test1', '1111');
INSERT INTO users (nickname, email, password) VALUES ('2', 'test2', '2222');
INSERT INTO users (nickname, email, password) VALUES ('3', 'test3', '3333');

INSERT INTO BOARD_AUTHORITY (auth_create_post_level, auth_access_board_level, auth_comment_level) VALUES (5, 5, 5);
INSERT INTO BOARD_AUTHORITY (auth_create_post_level, auth_access_board_level, auth_comment_level) VALUES (1, 1, 1);
INSERT INTO BOARD_AUTHORITY (auth_create_post_level, auth_access_board_level, auth_comment_level) VALUES (2, 2, 2);

INSERT INTO POST_AUTHORITY (auth_access_board_level, auth_comment_level) VALUES (5, 5);
INSERT INTO POST_AUTHORITY (auth_access_board_level, auth_comment_level) VALUES (4, 4);
INSERT INTO POST_AUTHORITY (auth_access_board_level, auth_comment_level) VALUES (3, 3);

INSERT INTO BOARD (board_type, page_post_count, description, auth_id) VALUES (1, 10, '자유게시판', 1);
INSERT INTO BOARD (board_type, page_post_count, description, auth_id) VALUES (2, 10, '모집게시판', 2);
INSERT INTO BOARD (board_type, page_post_count, description, auth_id) VALUES (3, 10, '평가게시판', 3);

INSERT INTO POST (board_id, user_id, title, content, auth_id) VALUES (1, 1, 'title1', 'content1', 1);
INSERT INTO POST (board_id, user_id, title, content, auth_id) VALUES (2, 1, 'title2', 'content2', 2);
INSERT INTO POST (board_id, user_id, title, content, auth_id) VALUES (3, 2, 'title3', 'content3', 3);
