# URL of Load Balancer (application access)
output "alb_url" {
  description = "URL to access the application"
  value       = "http://${aws_lb.main.dns_name}"
}

# URL of ECR repository (for pushing Docker images)
output "ecr_repository_url" {
  description = "ECR repository URL"
  value       = aws_ecr_repository.main.repository_url
}

# Endpoint for database access
output "db_endpoint" {
  description = "RDS PostgresSQL endpoint"
  value       = aws_db_instance.main.endpoint
}

# ECS cluster name
output "ecs_cluster_name" {
  description = "ECS Cluster Name"
  value       = aws_ecs_cluster.main.name
}

# ECS service name
output "ecs_service_name" {
  description = "ECS Service Name"
  value       = aws_ecs_service.main.name
}
