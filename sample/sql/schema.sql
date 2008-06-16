alter table BookType drop constraint FK7B5705E390164CB9;
alter table BookType drop constraint FK7B5705E31D4E9620;
drop table AuthorName;
drop table BookType;
drop table BookstoreType;
drop sequence hibernate_sequence;
create table AuthorName (
    Hjid int8 not null,
    Hjtype varchar(255) not null,
    Hjversion int8 not null,
    FirstName varchar(255),
    LastName varchar(255),
    primary key (Hjid)
);
create table BookType (
    Hjid int8 not null,
    Hjtype varchar(255) not null,
    Hjversion int8 not null,
    Price numeric,
    Genre varchar(255),
    Author int8,
    Title varchar(255),
    BookstoreType_Book_Hjid int8,
    BookstoreType_Book_Hjindex int4,
    primary key (Hjid)
);
create table BookstoreType (
    Hjid int8 not null,
    Hjtype varchar(255) not null,
    Hjversion int8 not null,
    primary key (Hjid)
);
alter table BookType 
    add constraint FK7B5705E390164CB9 
    foreign key (BookstoreType_Book_Hjid) 
    references BookstoreType;
alter table BookType 
    add constraint FK7B5705E31D4E9620 
    foreign key (Author) 
    references AuthorName;
create sequence hibernate_sequence;
