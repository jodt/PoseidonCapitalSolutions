/*Create users*/
insert into Users(fullname, username, password, role) values("Administrator", "admin", "$2a$10$VfcQQtNoJip36evWCXEzjuk6crcRRPCvEaEoXQxSTIMqLsQ6Vqd2C", "ADMIN");
insert into Users(fullname, username, password, role) values("User", "user", "$2a$10$QVFHSvjnbVIbtAggWi0C4ezDtPqdndwKdO/4NXNlb2RdWa2o/6Fzq", "USER");

/*Create bidList*/
INSERT INTO bidlist (account, type, bidQuantity)
    VALUES
        ('account', 'type', 10);

/*Create curvePoint*/
INSERT INTO curvepoint (term, value)
    VALUES
        (10, 10);

/*Create rating*/
INSERT INTO rating (fitchRating, moodysRating, sandPRating, orderNumber)
    VALUES
        ('fitch', 'moodys', 'sandPRating', 10);

/*Create trade*/
INSERT INTO trade (account, type, buyQuantity)
    VALUES
        ('account', 'type', 10);

/*Create rule*/
INSERT INTO rulename (description, json, name, sqlPart, sqlStr, template)
    VALUES
        ('description', 'json', 'name', 'sqlPart', 'sqlStr', 'template');