SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS atm_cases;

CREATE TABLE atm_cases (
                           id                    serial,
                           case_id               INT(11) NOT NULL,
                           atm_id                VARCHAR(50) NOT NULL,
                           case_desc             TEXT,
                           start                 DATETIME,
                           finish                DATETIME,
                           serial                VARCHAR(100),
                           bank_nm               VARCHAR(100),
                           channel               VARCHAR(50),
                           PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;