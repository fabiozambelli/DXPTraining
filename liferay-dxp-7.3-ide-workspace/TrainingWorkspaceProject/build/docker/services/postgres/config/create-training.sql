drop database if exists training; 
create database training; 
create user training with encrypted password 'training';
grant all privileges on database training to training;
