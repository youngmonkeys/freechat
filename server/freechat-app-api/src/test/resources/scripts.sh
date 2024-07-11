use freechat-test
db.createUser(
   {
     user:"freechat-test",
     pwd:"123456",
     roles:[ { role: "readWrite", db: "freechat-test" }]
   }
)


CREATE SCHEMA `freechat-test` DEFAULT CHARACTER SET utf8mb4 ;

CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `isRead` tinyint(1) DEFAULT NULL,
  `message` varchar(300) DEFAULT NULL,
  `channelId` bigint DEFAULT NULL,
  `sender` varchar(45) DEFAULT NULL,
  `sentClientMessageId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
