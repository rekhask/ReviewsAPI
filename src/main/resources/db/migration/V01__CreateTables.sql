
-- db name: ProductReviews
-- Run this statement to create a database --
-- Create database ProductReview;
-- --------------------------------------------
-- Create Tables

-- 1. Products Table
Create Table product (
	product_id INTEGER auto_increment,
    name varchar(100),
    description varchar(5000),
    constraint product_id_primary primary key (product_id)
);

-- 2. Review Table
 create table review (
	review_id  INTEGER auto_increment,
	title varchar(50),
    text varchar(3000),
    product_id INTEGER,
    constraint review_id_pk primary key (review_id),
    constraint review_product_id_fk
     foreign key (product_id) references product (product_id)
 );

-- 3. Comments Table
create table comments (
	comment_id INTEGER auto_increment not null,
   review_id INTEGER,
   text varchar(2000),
   constraint comment_id_pk primary key (comment_id),
   constraint comment_review_id_fk
	foreign key (review_id) references review (review_id)
);
