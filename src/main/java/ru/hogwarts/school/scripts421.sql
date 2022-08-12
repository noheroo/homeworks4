alter table students
    add constraint age_constraint check ( age > 16);

alter table students
    add constraint name_constraint unique (name),
    alter column name set not null;

alter table faculties
    add constraint name_color_unique_constraint unique (name, color);

alter table students
    alter column age set default 20;