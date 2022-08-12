select students.name, students.age, faculties.name
from students
         inner join faculties on students.faculty_id = faculties.id;

select students.name, avatars
from students
         inner join avatars on students.id = avatars.student_id;
