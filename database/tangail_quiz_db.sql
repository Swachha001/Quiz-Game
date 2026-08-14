-- Tangail District Quiz
-- Run this file once in MySQL to create the database and the 20 starter questions.

CREATE DATABASE IF NOT EXISTS tangail_quiz_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE tangail_quiz_db;

-- Drop in reverse order because of foreign keys
DROP TABLE IF EXISTS quiz_answers;
DROP TABLE IF EXISTS quiz_attempts;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS players;

CREATE TABLE players (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_text TEXT NOT NULL,
    option_a VARCHAR(500) NOT NULL,
    option_b VARCHAR(500) NOT NULL,
    option_c VARCHAR(500) NOT NULL,
    option_d VARCHAR(500) NOT NULL,
    correct_option CHAR(1) NOT NULL,
    category VARCHAR(50) NOT NULL,
    difficulty VARCHAR(20) DEFAULT 'Easy',
    explanation TEXT,
    source_url VARCHAR(1000),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE quiz_attempts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    player_id INT NOT NULL,
    total_questions INT NOT NULL,
    correct_answers INT NOT NULL,
    wrong_answers INT NOT NULL,
    score INT NOT NULL,
    percentage DECIMAL(5,2) NOT NULL,
    started_at TIMESTAMP NULL,
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);

CREATE TABLE quiz_answers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    attempt_id INT NOT NULL,
    question_id INT NOT NULL,
    selected_option CHAR(1),
    correct_option CHAR(1) NOT NULL,
    is_correct BOOLEAN NOT NULL,
    FOREIGN KEY (attempt_id) REFERENCES quiz_attempts(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

CREATE INDEX idx_questions_active ON questions (active);
CREATE INDEX idx_questions_category ON questions (category);
CREATE INDEX idx_attempts_player ON quiz_attempts (player_id);
CREATE INDEX idx_answers_attempt ON quiz_answers (attempt_id);

-- 20 starter questions. Correct option is always A, B, C, or D.

INSERT INTO questions (
    question_text, option_a, option_b, option_c, option_d,
    correct_option, category, difficulty, explanation, source_url, active
) VALUES
(
    'Which of the following is one of the major crops of Tangail District?',
    'Tea', 'Rice', 'Coffee', 'Rubber',
    'B', 'Crops & Agriculture', 'Easy',
    'Tangail''s official district overview lists rice among its major crops.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which crop is traditionally important in Tangail along with rice and sugarcane?',
    'Jute', 'Cocoa', 'Olive', 'Tobacco',
    'A', 'Crops & Agriculture', 'Easy',
    'The official district overview identifies jute as one of Tangail''s principal crops.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which of the following is listed as a major crop of Tangail District?',
    'Sugarcane', 'Apple', 'Tea', 'Coconut',
    'A', 'Crops & Agriculture', 'Easy',
    'Sugarcane is listed among Tangail''s major agricultural crops.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which of these is a major winter/rabi crop mentioned in the official overview of Tangail?',
    'Mustard', 'Tea', 'Rubber', 'Coconut',
    'A', 'Crops & Agriculture', 'Medium',
    'The Tangail district overview lists mustard among the district''s major crops.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which agricultural crop is mentioned as a major crop of Tangail together with rice, jute, sugarcane and mustard?',
    'Wheat', 'Coffee', 'Tea', 'Cotton',
    'A', 'Crops & Agriculture', 'Easy',
    'Wheat appears in the official Tangail crop list.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which of the following is specifically mentioned among Tangail''s major agricultural products?',
    'Vegetables', 'Dates', 'Tea leaves', 'Cocoa beans',
    'A', 'Crops & Agriculture', 'Easy',
    'The district''s official overview includes vegetables among the major agricultural crops and products.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which public university is located in Santosh, Tangail?',
    'University of Dhaka',
    'Mawlana Bhashani Science and Technology University',
    'Bangladesh University of Engineering and Technology',
    'Jahangirnagar University',
    'B', 'Academic Institutions', 'Easy',
    'MBSTU''s official site gives its location as Santosh, Tangail.',
    'https://mbstu.ac.bd/', TRUE
),
(
    'Mawlana Bhashani Science and Technology University is named after which prominent political leader?',
    'A. K. Fazlul Huq',
    'Maulana Abdul Hamid Khan Bhashani',
    'Huseyn Shaheed Suhrawardy',
    'Sher-e-Bangla A. K. Fazlul Huq',
    'B', 'Academic Institutions', 'Medium',
    'The university officially states that it was named after Mawlana Abdul Hamid Khan Bhashani.',
    'https://mbstu.ac.bd/', TRUE
),
(
    'In which year was Mawlana Bhashani Science and Technology University established?',
    '1991', '1995', '2001', '2010',
    'C', 'Academic Institutions', 'Medium',
    'MBSTU''s official overview says the university was established in 2001 and began academic activities in 2003.',
    'https://mbstu.ac.bd/', TRUE
),
(
    'Which two departments were the first to begin academic activities at MBSTU?',
    'CSE and ICT',
    'Physics and Chemistry',
    'Civil Engineering and Architecture',
    'Mathematics and Statistics',
    'A', 'Academic Institutions', 'Hard',
    'MBSTU''s official overview states that its first academic batch began in Computer Science and Engineering and Information and Communication Technology.',
    'https://mbstu.ac.bd/', TRUE
),
(
    'Which cadet college is located in Mirzapur, Tangail?',
    'Faujdarhat Cadet College',
    'Rajshahi Cadet College',
    'Mirzapur Cadet College',
    'Jhenaidah Cadet College',
    'C', 'Academic Institutions', 'Easy',
    'The Tangail district overview identifies Mirzapur Cadet College as the district''s cadet college.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which of the following is recognized by Tangail District Council as a notable educational institution in Tangail?',
    'Tangail Medical College',
    'Chittagong Medical College',
    'Rajshahi University',
    'Sylhet Cadet College',
    'A', 'Academic Institutions', 'Easy',
    'Tangail District Council lists Tangail Medical College among the district''s notable educational institutions.',
    'https://zp.tangail.gov.bd/', TRUE
),
(
    'Which institution is specifically associated with textile engineering education in Tangail?',
    'Tangail Textile Engineering College',
    'Bangladesh Textile University',
    'National Textile University, Dhaka',
    'Bangladesh Institute of Marine Technology',
    'A', 'Academic Institutions', 'Medium',
    'Tangail District Council lists Tangail Textile Engineering College among notable educational institutions.',
    'https://zp.tangail.gov.bd/', TRUE
),
(
    'Which major river forms an important part of the western geographical setting of Tangail District?',
    'Karnaphuli', 'Jamuna', 'Surma', 'Sangu',
    'B', 'Geography', 'Easy',
    'The official Tangail overview describes the district in relation to the Jamuna and lists it among the district''s major rivers.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'How many upazilas does Tangail District have?',
    '8', '10', '12', '15',
    'C', 'Geography', 'Easy',
    'The official district information identifies 12 upazilas.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which district is located to the north of Tangail?',
    'Dhaka', 'Jamalpur', 'Gazipur', 'Manikganj',
    'B', 'Geography', 'Easy',
    'Tangail''s official geographic boundary information identifies Jamalpur to the north.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which two districts are located to the south of Tangail?',
    'Jamalpur and Mymensingh',
    'Dhaka and Manikganj',
    'Gazipur and Mymensingh',
    'Sirajganj and Jamalpur',
    'B', 'Geography', 'Medium',
    'The official Tangail geographic description gives Dhaka and Manikganj as southern neighboring districts.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which district lies to the west of Tangail?',
    'Sirajganj', 'Gazipur', 'Mymensingh', 'Jamalpur',
    'A', 'Geography', 'Easy',
    'Sirajganj is identified as Tangail''s western neighboring district.',
    'https://www.tangail.gov.bd/', TRUE
),
(
    'Which river flows through/near Tangail town and is described by the Bangladesh Water Development Board as an 85-kilometre-long river?',
    'Lohajang', 'Karnaphuli', 'Padma', 'Teesta',
    'A', 'Geography', 'Hard',
    'The Bangladesh Water Development Board''s Tangail page gives the Lohajang River''s length as 85 km and describes its course through Tangail town.',
    'https://bwdb.tangail.gov.bd/', TRUE
),
(
    'Which of the following is one of the 12 upazilas of Tangail District?',
    'Sakhipur', 'Trishal', 'Kalmakanda', 'Singair',
    'A', 'Geography', 'Easy',
    'The official upazila list includes Sakhipur among Tangail''s 12 upazilas.',
    'https://www.tangail.gov.bd/', TRUE
);
