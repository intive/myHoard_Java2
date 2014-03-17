CREATE TABLE IF NOT EXISTS Media (
	id INT AUTO_INCREMENT,
	file MEDIUMBLOB,
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	item INT,

	PRIMARY KEY(id),
	FOREIGN KEY(item) REFERENCES Item(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

