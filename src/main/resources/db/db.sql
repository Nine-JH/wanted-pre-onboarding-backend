CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT,
    email VARCHAR(255),
    password VARCHAR(255)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS article (
                         id BIGINT AUTO_INCREMENT,
                         title VARCHAR(255),
                         content VARCHAR(255),
                         user_id BIGINT
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
