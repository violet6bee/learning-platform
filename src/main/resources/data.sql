-- =========================
-- USERS
-- =========================
INSERT INTO public.users (id, email, "name", "password", "role", second_name) VALUES
                                                                                  (1, 'eva@yandex.ru', 'Eva', 'eva', 'STUDENT', 'Luchina'),
                                                                                  (2, 'pavel@mail.ru', 'Pavel', 'pavel', 'TEACHER', 'Krasnov'),
                                                                                  (3, 'sveta@yandex.ru', 'Svetlana', 'svetlana', 'STUDENT', 'Morozova'),
                                                                                  (4, 'misha@gmail.ru', 'Misha', 'misha', 'STUDENT', 'Volskii');

-- =========================
-- PROFILES
-- =========================
INSERT INTO public.profiles (avatar_url, biography, phone, user_id) VALUES
                                                                        ('kitti', 'Love cats', '89648363719', 1),
                                                                        ('Pavel', 'Love teaching', '89726489725', 2),
                                                                        ('cute dog', 'Love dogs', '89725689725', 3),
                                                                        ('Summer', 'Love summer', '89765437896', 4);

-- =========================
-- CATEGORIES
-- =========================
INSERT INTO public.categories (id, "name") VALUES
                                               (1, 'Programming'),
                                               (2, 'Design'),
                                               (3, 'Marketing');

-- =========================
-- COURSES
-- =========================
INSERT INTO public.courses (id, title, description, category_id, teacher_id) VALUES
                                                                                 (1, 'Java', 'Learning Java', 1, 2),
                                                                                 (2, 'Hibernate', 'Learning Hibernate', 1, 2);

-- =========================
-- TAGS
-- =========================
INSERT INTO public.tags (id, "name") VALUES
                                         (1, 'Java'),
                                         (2, 'Hibernate'),
                                         (3, 'Beginner');

INSERT INTO public.course_tag (course_id, tag_id) VALUES
                                                      (1, 1),
                                                      (2, 2);

-- =========================
-- MODULES
-- =========================
INSERT INTO public.modules (id, title, description, order_index, course_id) VALUES
                                                                                (1, 'Learning the basics', 'Learning the basics', 1, 1),
                                                                                (2, 'Learning primitives', 'Learning primitives', 2, 1);

-- =========================
-- LESSONS
-- =========================
INSERT INTO public.lessons (id, title, video_url, module_id) VALUES
                                                                 (1, 'Introduction Lesson. Part 1', 'https://example.com/video1', 1),
                                                                 (2, 'Introduction Lesson. Part 2', 'https://example.com/video2', 1);

-- =========================
-- ASSIGNMENTS
-- =========================
INSERT INTO public.assignments (id, title, description, due_date, max_score, lesson_id) VALUES
    (1, 'Homework 1', 'Solve basic Java tasks', '2025-12-31 23:59:00', 100, 2);

-- =========================
-- QUIZZES
-- =========================
INSERT INTO public.quizzes (id, title, course_id) VALUES
                                                      (1, 'Java Basics Quiz', 1),
                                                      (2, 'Hibernate Basics Quiz', 2);

-- =========================
-- QUESTIONS
-- =========================
INSERT INTO public.questions (id, "text", type_choices, quiz_id) VALUES
                                                                     (1, 'What is Java?', 'SINGLE_CHOICE', 1),
                                                                     (2, 'What is Long?', 'SINGLE_CHOICE', 1);

-- =========================
-- ANSWER OPTIONS
-- =========================
INSERT INTO public.answer_options (id, is_correct, "text", question_id) VALUES
                                                                            (1, false, 'Java is a cat', 1),
                                                                            (2, true, 'Java is a programming language', 1),

                                                                            (3, false, 'Long is a floating-point type', 2),
                                                                            (4, true, 'Long is a primitive data type for large integers', 2);

-- =========================
-- QUIZ SUBMISSIONS
-- =========================
INSERT INTO public.quiz_submissions (id, score, taken_at, quiz_id, student_id) VALUES
                                                                                   (1, 90, '2025-12-20 19:00:00', 1, 3),
                                                                                   (2, 0, '2025-12-25 16:25:54', 1, 3);

-- =========================
-- SUBMISSIONS
-- =========================
INSERT INTO public.submissions (id, "content", feedback, score, submitted_at, assignment_id, student_id) VALUES
    (1, 'My homework solution text', 'Good job', 85, '2025-12-20 18:30:00', 1, 3);
