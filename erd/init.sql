SELECT version();

-- User's login information --
CREATE TABLE users(
  id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, 
  username VARCHAR(128) NOT NULL UNIQUE,
  password TEXT NOT NULL,
  -- authorized columns --
  active BOOLEAN DEFAULT TRUE,
  created_by INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_by INT,
  updated_at TIMESTAMP,
  deleted_by INT,
  deleted_at TIMESTAMP
);

-- User's information detailed --
CREATE TABLE shared_videos(
  video_key VARCHAR(128) PRIMARY KEY,
  video_url VARCHAR(2048) NOT NULL,
  -- authorized columns --
  active BOOLEAN DEFAULT TRUE,
  shared_by INT REFERENCES users(id),
  shared_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_by INT,
  deleted_at TIMESTAMP
);