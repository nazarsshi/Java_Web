CREATE TABLE IF NOT EXISTS volunteer
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(20)        NOT NULL,
    last_name     VARCHAR(20),
    email         VARCHAR(30) UNIQUE NOT NULL,
    password      VARCHAR(30)        NOT NULL,
    phone         VARCHAR(11) UNIQUE,
    about_user    VARCHAR(2048),
    photo         VARCHAR(2048),
    date_of_birth DATE,
    trust_level   VARCHAR(32),
    is_blocked    BOOLEAN DEFAULT FALSE,
    api_key       VARCHAR(50) UNIQUE
);

CREATE TABLE IF NOT EXISTS category
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50)  NOT NULL,
    description   VARCHAR(128) NOT NULL
);


ALTER TABLE category
    ADD UNIQUE (category_name, description);

CREATE TABLE IF NOT EXISTS task
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    description   VARCHAR(255) NOT NULL,
    creation_date DATE,
    title         VARCHAR(30)  NOT NULL,
    participants  INT,
    status        VARCHAR(30)  NOT NULL ,
    end_date      DATE         NOT NULL,
    priority      VARCHAR(30),
    category_id   INT          NOT NULL,
    creator_id    INT          NOT NULL,

    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
            REFERENCES category (id),

    CONSTRAINT fk_creator
        FOREIGN KEY (creator_id)
            REFERENCES volunteer (id)

);

CREATE TABLE IF NOT EXISTS volunteer_task
(
    volunteer_id       INT  NOT NULL,
    task_id            INT  NOT NULL,
    participation_date DATE NOT NULL,
    comment            VARCHAR(50),
    approved           BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (volunteer_id, task_id),

    CONSTRAINT fk_volunteer_volunteer_task
        FOREIGN KEY (volunteer_id)
            REFERENCES volunteer (id),

    CONSTRAINT fk_task_volunteer_task
        FOREIGN KEY (task_id)
            REFERENCES task (id)
);

-- INSERT INTO category (category_name, description)
-- VALUES ('Disabled', 'Helping disabled people'),
--        ('Elderly', 'Helping elderly'),
--        ('Fundraising', 'Raising funds'),
--        ('Emigration', 'Helping emigrants'),
--        ('Immigration', 'Helping immigrants'),
--        ('City infrastructure', 'Helping improving city infrastructure'),
--        ('Children', 'Helping children');

-- INSERT INTO volunteer(first_name, last_name, email, password, phone, photo, date_of_birth, trust_level, is_blocked,
--                       api_key)
-- VALUES ('Nazar', 'Koval', 'marmeladka228@gmail.com', 'Kebab1488', '0679359820', 'photo 1', '2001-01-20', 'NOVICE',
--         false, 'testKey1'),
--        ('Yura', 'Khanas', 'yura1@gmail.com', 'Kaban228', '0990095274', 'photo 2', '2000-12-17', 'NOVICE', true,
--         'testKey2'),
--        ('Test', 'One', 'test1@gmail.com', 'Kaban228', '0990095275', 'photo 3', '2000-12-17', 'NOVICE', false,
--         'testKey3'),
--        ('Test', 'Two', 'test2@gmail.com', 'Kaban228', '0990095271', 'photo 4', '2000-12-17', 'NOVICE', false,
--         'testKey4'),
--        ('Test', 'Three', 'test3@gmail.com', 'Kaban228', '0990095272', 'photo 5', '2000-09-17', 'NOVICE', false,
--         'testKey5'),
--        ('Test', 'Four', 'test4@gmail.com', 'Kaban228', '0990095273', 'photo 6', '2000-11-17', 'NOVICE', false,
--         'testKey5'),
--        ('Test', 'Five', 'test5@gmail.com', 'Kaban228', '0990095274', 'photo 7', '2000-10-17', 'NOVICE', false,
--         'testKey6'),
--        ('Test', 'Six', 'test6@gmail.com', 'Kaban228', '0990095275', 'photo 8', '2000-08-17', 'NOVICE', false,
--         'testKey7'),
--        ('Test', 'Seven', 'test7@gmail.com', 'Kaban228', '0990095276', 'photo 9', '2000-07-17', 'NOVICE', false,
--         'testKey8'),
--        ('Test', 'Eight', 'test8@gmail.com', 'Kaban228', '0990095277', 'photo 10', '2000-06-17', 'NOVICE', false,
--         'testKey9'),
--        ('Test', 'Nine', 'test9@gmail.com', 'Kaban228', '0990095278', 'photo 11', '2000-05-17', 'NOVICE', false,
--         'testKey10');
--
-- INSERT INTO task(description, creation_date, title, participants, status, end_date, category_id, creator_id)
-- VALUES ('First description', '2020-05-05', 'FirstTitle', 10,'PENDING','2020-07-17', 1, 4),
--        ('Second description', '2020-03-27', 'SecondTitle', 3,'PENDING','2020-05-15', 3, 2),
--        ('Third description', '2019-09-07', 'ThirdTitle', 2,'PENDING','2020-10-10', 2, 2),
--        ('Fourth description', '2018-09-09', 'FourthTitle', 4,'PENDING','2020-12-12', 4, 3),
--        ('Fifth description', '2020-06-05', 'FifthTitle', 5,'PENDING','2020-07-17', 6, 7),
--        ('Sixth description', '2020-03-27', 'SixthTitle', 8,'PENDING','2020-05-15', 5, 5),
--        ('Seventh description', '2019-09-07', 'SeventhTitle', 9,'PENDING','2020-10-10', 7, 8),
--        ('Eighth description', '2018-09-18', 'EighthTitle', 7,'PENDING','2020-12-11', 4, 10),
--        ('Ninth description', '2018-12-17', 'NinthTitle', 6,'PENDING','2020-11-12', 3, 6),
--        ('Tenth description', '2018-10-10', 'TenthTitle', 7,'PENDING','2020-11-11', 1, 10);
--
-- INSERT INTO volunteer_task (volunteer_id, task_id, participation_date, comment, approved)
-- VALUES (1, 2, '2020-03-30', 'first user - first task', true),
--        (3, 3, '2019-09-27', 'third user - third task', false),
--        (2, 4, '2020-05-07', 'second user - fourth task', true),
--        (4, 5, '2020-05-07', 'fourth user - fifth task', false),
--        (7, 6, '2020-05-07', 'seventh user - sixth task', true),
--        (5, 7, '2020-05-07', 'fifth user - seventh task', false),
--        (7, 8, '2020-05-07', 'seventh user - eighth task', true);