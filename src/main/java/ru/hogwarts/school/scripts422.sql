create table cars
(
    id   smallserial primary key,
    manufacturer text unique not null,
    model text not null,
    cost int check ( cost > 0 )
);

create table drivers
(
    id            smallserial primary key,
    name          text unique not null,
    age           int check ( age > 0 ),
    driverLicence bool default false,
    car_id        smallserial references cars (id)
);


