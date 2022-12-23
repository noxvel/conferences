USE `conferences` ;

SET @lorem_ipsum = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Nec sagittis aliquam malesuada bibendum arcu vitae elementum curabitur. Orci sagittis eu volutpat odio facilisis mauris. Arcu risus quis varius quam quisque id diam. Eu mi bibendum neque egestas congue quisque egestas. Interdum posuere lorem ipsum dolor. Feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam. Sed odio morbi quis commodo. Eu augue ut lectus arcu bibendum at varius vel. Non arcu risus quis varius quam quisque. Amet massa vitae tortor condimentum lacinia quis vel eros. Sit amet commodo nulla facilisi nullam vehicula ipsum a arcu. Non consectetur a erat nam at lectus urna. Odio euismod lacinia at quis risus sed vulputate odio. Lobortis feugiat vivamus at augue.';
-- TEST DATA
-- -----------------------------------------------------
INSERT INTO user_role (id, name)  VALUES (DEFAULT, 'MODERATOR');
INSERT INTO user_role (id, name)  VALUES (DEFAULT, 'SPEAKER');
INSERT INTO user_role (id, name)  VALUES (DEFAULT, 'ORDINARY_USER');
-- -----------------------------------------------------
INSERT INTO user (id, email, password, user_role_id, first_name, last_name) VALUES (DEFAULT, 'moderator@mail.com', md5('123'), 1, 'Іван', 'Гармата');
INSERT INTO user (id, email, password, user_role_id, first_name, last_name) VALUES (DEFAULT, 'speaker@mail.com', md5('123'), 2, 'Богдан', 'Хмельницький');
INSERT INTO user (id, email, password, user_role_id, first_name, last_name) VALUES (DEFAULT, 'user@mail.com', md5('123'), 3, 'Микола', 'Пупкін');
INSERT INTO user (id, email, password, user_role_id, first_name, last_name) VALUES (DEFAULT, 'user2@mail.com', md5('123'), 3, 'Леся', 'Українка');
-- -----------------------------------------------------
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'FREE');
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'OFFERED_BY_SPEAKER');
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'PROPOSE_TO_SPEAKER');
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'SUGGESTED_SPEAKER');
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'CONFIRMED');
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'CANCELED');
-- -----------------------------------------------------
INSERT INTO event (id, name, place, begin_date, end_date, description, participants_came)  VALUES (DEFAULT, 'Java conference', 'Odessa', '2022-02-18 13:00:00', '2022-02-21 12:00:00', @lorem_ipsum, 500);
INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'C++ conference', 'San Francisco', '2022-07-04 16:00:00', '2022-07-08 13:00:00', @lorem_ipsum);
INSERT INTO event (id, name, place, begin_date, end_date, description, participants_came)  VALUES (DEFAULT, 'Python conference', 'London', '2022-06-13 11:00:00', '2022-06-15 16:00:00', @lorem_ipsum, 200);
INSERT INTO event (id, name, place, begin_date, end_date, description, participants_came)  VALUES (DEFAULT, 'Ruby conference', 'Paris', '2022-08-22 16:00:00', '2022-08-24 17:30:00', @lorem_ipsum, 300);
INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'Kotlin conference', 'New York', '2022-04-12 08:00:00', '2022-04-14 16:00:00', @lorem_ipsum);
INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'Scala conference', 'Rio de Janeiro', '2022-01-14 08:00:00', '2022-01-15 16:00:00', @lorem_ipsum);
INSERT INTO event (id, name, place, begin_date, end_date, description, participants_came)  VALUES (DEFAULT, 'Android conference', 'Los Angeles', '2022-11-06 08:00:00', '2022-11-09 16:00:00', @lorem_ipsum, 150);
-- INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'Rust conference', 'Tokyo', '2022-07-17 08:00:00', '2022-07-19 16:00:00', @lorem_ipsum);
INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'JavaScript conference', 'Helsinki', '2022-12-12 08:00:00', '2022-12-16 16:00:00', @lorem_ipsum);

-- -----------------------------------------------------
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Java', NULL, 1, 1, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in Java', 2, 4, 1, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in C++', NULL, 1, 2, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in C++', 2, 2, 2, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Python', NULL, 1, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in Python', 2, 3, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Types in Python', 2, 2, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 1', NULL, 1, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 2', 2, 3, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 3', NULL, 1, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 4', NULL, 1, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 5', 2, 3, 3, @lorem_ipsum);

-- INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Ruby', NULL, 1, 4, @lorem_ipsum 
-- INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in Ruby', 2, 4, 4, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Kotlin', NULL, 1, 5, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in Kotlin', 2, 5, 5, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Async programming', NULL, 1, 5, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Scala', NULL, 1, 6, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Android', NULL, 1, 7, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in Android', 2, 3, 7, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in JavaScript', NULL, 1, 8, @lorem_ipsum);


-- -----------------------------------------------------
INSERT INTO event_has_participant (event_id, user_id)  VALUES (1, 3);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (2, 3);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (4, 3);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (2, 4);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (3, 4);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (4, 4);



