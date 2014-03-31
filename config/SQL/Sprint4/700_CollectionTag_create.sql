CREATE TABLE IF NOT EXISTS CollectionTag (
	collection INT NOT NULL,
	tag INT NOT NULL,

	PRIMARY KEY(collection, tag),
	FOREIGN KEY(collection) REFERENCES Collection(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(tag) REFERENCES Tag(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
