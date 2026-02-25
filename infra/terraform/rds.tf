# Subnet Group
# Define in which subnets the RDS can be created(needs at least 2 available zones(AZ) for high availability)
resource "aws_db_subnet_group" "main" {
  name        = "${var.project_name}-${var.environment}-db-subnet"
  description = "Subnet group for RDS"
  subnet_ids  = [aws_subnet.private_1.id, aws_subnet.private_2.id]

  tags = {
    Name = "${var.project_name}-${var.environment}-db-subnet"
  }
}

# RDS PostgresSQL Instance
resource "aws_db_instance" "main" {
  identifier = "${var.project_name}-${var.environment}-db"

  # Engine
  engine         = "postgres"
  engine_version = "16.3"

  # Volume (min 20GB for Postgres)
  instance_class    = "db.t3.micro"
  allocated_storage = 20
  storage_type      = "gp2"

  # Credentials
  db_name  = "tinyurl"
  username = var.db_username
  password = var.db_password

  # Network
  vpc_security_group_ids = [aws_security_group.rds.id]
  db_subnet_group_name   = aws_db_subnet_group.main.name
  publicly_accessible    = false

  # Backup
  backup_retention_period = 7
  backup_window           = "03:00-04:00"

  # Maintenance
  maintenance_window = "Mon:04:00-Mon:05:00"

  # Dev environment: allows deleting without final snapshot
  skip_final_snapshot = var.environment == "dev" ? true : false

  # Disable automatic upgrades of major versions
  auto_minor_version_upgrade = true

  tags = {
    Name = "${var.project_name}-${var.environment}-db"
  }
}
