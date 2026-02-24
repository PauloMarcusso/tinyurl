variable "aws_region" {
    description = "The AWS region to deploy resources in."
    type        = string
    default     = "us-east-1"
}

variable "project_name" {
  description = "Project name for resource naming."
  type = string
  default = "tinyurl"
}

variable "environment" {
  description = "Environment (dev,prod)"
  type = string
  default = "dev"
}

variable "db_username" {
  description = "Database username"
  type = string
  default = "postgres"
}

variable "db_password" {
  description = "Database password"
  type = string
  sensitive = true
}