    id UUID PRIMARY KEY,
CREATE TABLE short_urls (
    short_code VARCHAR(10) NOT NULL,
    original_url TEXT NOT NULL,
    clicks BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NULL,

    CONSTRAINT uk_short_code UNIQUE (short_code)
);

CREATE INDEX idx_short_urls_short_code ON short_urls(short_code);
CREATE INDEX idx_short_urls_created_at ON short_urls(created_at);

CREATE TABLE url_accesses (

id UUID PRIMARY KEY,
short_url_id UUID NOT NULL,
ip_address VARCHAR(45),
user_agent TEXT,
referer TEXT,
accessed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

CONSTRAINT fk_short_url
  FOREIGN KEY (short_url_id) REFERENCES short_urls(id) ON DELETE CASCADE
);

CREATE INDEX idx_url_accesses_short_url_id ON url_accesses(short_url_id);
CREATE INDEX idx_url_accesses_accessed_at ON url_accesses(accessed_at);

CREATE INDEX idx_url_accesses_url_date ON url_accesses(short_url_id, accessed_at);
