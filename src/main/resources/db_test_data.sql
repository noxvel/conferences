USE `conferences` ;

SET @lorem_ipsum = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Nec sagittis aliquam malesuada bibendum arcu vitae elementum curabitur. Orci sagittis eu volutpat odio facilisis mauris. Arcu risus quis varius quam quisque id diam. Eu mi bibendum neque egestas congue quisque egestas. Interdum posuere lorem ipsum dolor. Feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam. Sed odio morbi quis commodo. Eu augue ut lectus arcu bibendum at varius vel. Non arcu risus quis varius quam quisque. Amet massa vitae tortor condimentum lacinia quis vel eros. Sit amet commodo nulla facilisi nullam vehicula ipsum a arcu. Non consectetur a erat nam at lectus urna. Odio euismod lacinia at quis risus sed vulputate odio. Lobortis feugiat vivamus at augue.';
SET @password_123 = '$argon2id$v=19$m=15360,t=2,p=1$92jtoPD/y0MH+RcdCoQJzeTGkSUDLP+cmPSyuHNXlPM$ZXhKUprhgIfBPgl3alhPswzzKMaknU9qP0nvJMZxKgo4wI19CIVaOAt9wRJrA0K7/wGZOeZOi2wELnjqHiTv9Q';
-- TEST DATA
-- -----------------------------------------------------
INSERT INTO user_role (id, name)  VALUES (DEFAULT, 'MODERATOR');
INSERT INTO user_role (id, name)  VALUES (DEFAULT, 'SPEAKER');
INSERT INTO user_role (id, name)  VALUES (DEFAULT, 'ORDINARY_USER');
-- -----------------------------------------------------
INSERT INTO user (id, email, password, user_role_id, first_name, last_name, receive_notifications) VALUES (DEFAULT, 'moderator@mail.com', @password_123, 1, 'Іван', 'Гармата', DEFAULT);
INSERT INTO user (id, email, password, user_role_id, first_name, last_name, receive_notifications) VALUES (DEFAULT, 'speaker@mail.com', @password_123, 2, 'Богдан', 'Хмельницький', 1);
INSERT INTO user (id, email, password, user_role_id, first_name, last_name, receive_notifications) VALUES (DEFAULT, 'speaker2@mail.com', @password_123, 2, 'Іван', 'Франко', DEFAULT);
INSERT INTO user (id, email, password, user_role_id, first_name, last_name, receive_notifications) VALUES (DEFAULT, 'user@mail.com', @password_123, 3, 'Микола', 'Пупкін', 1);
INSERT INTO user (id, email, password, user_role_id, first_name, last_name, receive_notifications) VALUES (DEFAULT, 'user2@mail.com', @password_123, 3, 'Леся', 'Українка', DEFAULT);
INSERT INTO user (id, email, password, user_role_id, first_name, last_name, receive_notifications) VALUES (DEFAULT, 'user3@mail.com', @password_123, 3, 'Володимир', 'Адамчук', DEFAULT);
-- -----------------------------------------------------
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'FREE');
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'OFFERED_BY_SPEAKER');
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'PROPOSE_TO_SPEAKER');
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'SUGGESTED_SPEAKER');
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'CONFIRMED');
INSERT INTO report_status (id, name)  VALUES (DEFAULT, 'CANCELED');
-- -----------------------------------------------------
INSERT INTO event (id, name, place, begin_date, end_date, description, participants_came)  VALUES (DEFAULT, 'Java conference', 'Odesa', '2023-02-18 13:00:00', '2023-02-21 12:00:00', ' Java has been a leading programming language for over two decades, and the Java Conference offers a unique opportunity for Java developers to meet face-to-face and collaborate with industry experts. Attendees can expect a comprehensive agenda covering a wide range of topics, from Java core concepts and language features to advanced frameworks, tools, and best practices. With workshops, keynotes, and interactive sessions, the conference is designed to engage attendees at every level of expertise. Participants can expect to deepen their knowledge and understanding of the Java ecosystem while networking with peers and building connections that can lead to new opportunities.', 500);
INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'C++ conference', 'San Francisco', '2023-07-04 16:00:00', '2023-07-08 13:00:00', 'C++ is a versatile and powerful programming language, widely used in industries such as gaming, finance, and manufacturing. The C++ Conference provides a dedicated platform for C++ enthusiasts to come together and share their passion for the language. The conference will feature in-depth discussions on C++ development, including the latest language standards, libraries, and best practices. Participants will have the opportunity to learn from some of the most experienced developers in the industry, attend hands-on workshops, and participate in networking events. Whether you are a beginner or a seasoned developer, the C++ Conference offers an immersive experience to learn, grow, and collaborate.');
INSERT INTO event (id, name, place, begin_date, end_date, description, participants_came)  VALUES (DEFAULT, 'Python conference', 'London', '2023-03-13 11:00:00', '2023-03-15 16:00:00', 'Python has become one of the most popular programming languages in recent years, thanks to its ease of use, versatility, and strong community support. The Python Conference offers a unique opportunity for Python developers and enthusiasts to come together and explore the latest trends and advancements in the language. The conference will feature keynote speeches, technical sessions, and workshops, covering a wide range of topics, from machine learning and data science to web development and automation. Participants will have the chance to connect with like-minded professionals, share their experiences, and build valuable relationships. Whether you are a beginner or an experienced Python developer, the Python Conference provides a valuable platform to enhance your skills, knowledge, and career.', 200);
INSERT INTO event (id, name, place, begin_date, end_date, description, participants_came)  VALUES (DEFAULT, 'Ruby conference', 'Paris', '2023-08-22 16:00:00', '2023-08-24 17:30:00', 'Ruby is a dynamic, object-oriented programming language that is used in a variety of applications, from web development to artificial intelligence. The Ruby Conference is designed for Ruby developers of all levels, from beginner to advanced. Attendees can expect a comprehensive agenda covering a wide range of topics, including language features, best practices, frameworks, and tools. The conference also offers networking opportunities with fellow developers and industry experts, as well as hands-on workshops and interactive sessions. Participants can expect to deepen their knowledge and understanding of the Ruby ecosystem while building connections that can lead to new opportunities.', 300);
INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'Kotlin conference', 'New York', '2022-04-12 08:00:00', '2022-04-14 16:00:00', 'Kotlin is a modern, concise programming language that has gained popularity in recent years, particularly for Android development. The Kotlin Conference provides a dedicated platform for Kotlin developers to learn about the latest language features, best practices, and tools. The conference will feature technical sessions, keynote speeches, and workshops, covering a wide range of topics, from language fundamentals to advanced concepts such as coroutines and multiplatform development. Participants will have the opportunity to network with like-minded professionals and industry experts, as well as engage in hands-on learning opportunities to deepen their skills and knowledge of the Kotlin ecosystem.');
INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'Scala conference', 'Rio de Janeiro', '2022-01-14 08:00:00', '2022-01-15 16:00:00', 'Scala is a general-purpose programming language that combines object-oriented and functional programming paradigms. The Scala Conference is designed for Scala developers and enthusiasts to come together and explore the latest advancements and trends in the language. The conference will feature keynote speeches, technical sessions, and workshops covering a range of topics, including language features, tooling, and frameworks. Participants can expect to deepen their understanding of Scala while networking with peers and industry experts, and engage in hands-on learning opportunities to enhance their skills and knowledge.');
INSERT INTO event (id, name, place, begin_date, end_date, description, participants_came)  VALUES (DEFAULT, 'Android conference', 'Los Angeles', '2022-11-06 08:00:00', '2022-11-09 16:00:00', 'The Android Conference is the premier event for Android developers, offering a comprehensive program covering all aspects of Android development. The conference will feature technical sessions, workshops, and keynotes covering the latest Android features and tools, as well as best practices for building high-quality apps. Attendees can also expect networking opportunities with fellow Android developers and industry experts, as well as engage in hands-on learning opportunities to enhance their skills and knowledge of Android development.', 150);
INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'JavaScript conference', 'Helsinki', '2022-12-12 08:00:00', '2022-12-16 16:00:00', 'JavaScript is a versatile programming language that is used for web development, server-side scripting, and more. The JavaScript Conference is designed for developers who want to stay up-to-date with the latest trends and advancements in the language. The conference will feature technical sessions, workshops, and keynotes covering a range of topics, including frameworks, libraries, and best practices. Participants will have the opportunity to network with peers and industry experts, as well as engage in hands-on learning opportunities to deepen their skills and knowledge of JavaScript development.');
INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'iOS conference', 'Praga', '2023-05-12 08:00:00', '2023-05-16 16:00:00', 'The iOS Conference is the premier event for iOS developers, offering a comprehensive program covering all aspects of iOS development. The conference will feature technical sessions, workshops, and keynotes covering the latest iOS features and tools, as well as best practices for building high-quality apps. Attendees can also expect networking opportunities with fellow iOS developers and industry experts, as well as engage in hands-on learning opportunities to enhance their skills and knowledge of iOS development.');
INSERT INTO event (id, name, place, begin_date, end_date, description)  VALUES (DEFAULT, 'Rust conference', 'Tokyo', '2023-07-17 08:00:00', '2023-07-19 16:00:00', 'Rust is a systems programming language that has gained popularity for its focus on safety and performance. The Rust Conference is designed for Rust developers and enthusiasts to come together and explore the latest trends and advancements in the language. The conference will feature keynote speeches, technical sessions, and workshops covering a range of topics, including language features, tooling, and frameworks. Participants will have the opportunity to network with peers and industry experts, as well as engage in hands-on learning opportunities to deepen their skills and knowledge of Rust development.');
-- -----------------------------------------------------
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Java', NULL, 1, 1, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in Java', 2, 5, 1, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 1', 3, 3, 1, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 2', 2, 3, 1, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in C++', NULL, 1, 2, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in C++', 2, 5, 2, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 1', 3, 5, 2, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 2', 2, 3, 2, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Python', NULL, 1, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in Python', 2, 3, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Types in Python', 2, 2, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 1', NULL, 1, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 2', 2, 5, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 3', NULL, 1, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 4', 3, 1, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 5', 3, 5, 3, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 6', 2, 5, 3, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Ruby', NULL, 1, 4, @lorem_ipsum); 
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in Ruby', 2, 4, 4, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Kotlin', NULL, 1, 5, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in Kotlin', 2, 5, 5, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Async programming', NULL, 1, 5, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 1', 3, 3, 5, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Scala', NULL, 1, 6, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in Android', NULL, 1, 7, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Old in Android', 2, 3, 7, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 2', 2, 5, 7, @lorem_ipsum);

INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'New in JavaScript', NULL, 1, 8, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 1', 3, 5, 8, @lorem_ipsum);
INSERT INTO report (id, topic, speaker_id, report_status_id, event_id, description)  VALUES (DEFAULT, 'Test report 2', 2, 3, 8, @lorem_ipsum);


-- -----------------------------------------------------
INSERT INTO event_has_participant (event_id, user_id)  VALUES (1, 4);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (2, 4);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (4, 4);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (1, 5);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (2, 5);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (3, 5);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (7, 5);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (4, 5);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (8, 5);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (9, 6);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (5, 6);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (7, 6);
INSERT INTO event_has_participant (event_id, user_id)  VALUES (1, 6);



