create table cars
(
    id   smallserial primary key,
    name text unique not null
);

create table drivers
(
    id            smallserial primary key,
    name          text unique not null,
    age           int check ( age > 0 ),
    driverLicence bool default false,
    car_id        smallserial references cars (id)
);

