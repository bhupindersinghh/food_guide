# 🚀 FoodLink Deployment Guide

## Deployment Options

### Option 1: Railway (Easiest - Recommended for MVP)

**Why Railway?**
- Free tier available
- Automatic PostgreSQL setup
- Easy environment variables
- GitHub integration
- Free SSL

**Steps:**
1. Push code to GitHub
2. Go to [railway.app](https://railway.app)
3. Create new project → Deploy from GitHub
4. Add PostgreSQL service
5. Set environment variables:
```
DB_HOST=postgres.railway.internal
DB_PORT=5432
DB_NAME=railway
DB_USERNAME=[auto-provided]
DB_PASSWORD=[auto-provided]
JWT_SECRET=[generate-secure-key]
```
6. Deploy! Railway auto-detects Spring Boot

**Cost**: Free for 500 hours/month

---

### Option 2: Heroku

**Steps:**
1. Install Heroku CLI
2. Create app:
```bash
heroku create foodlink-api
heroku addons:create heroku-postgresql:mini
```

3. Set environment variables:
```bash
heroku config:set JWT_SECRET=your-secret-key
heroku config:set SPRING_PROFILES_ACTIVE=prod
```

4. Deploy:
```bash
git push heroku main
```

**Cost**: $7/month (Eco dyno + PostgreSQL)

---

### Option 3: AWS EC2 (More Control)

**Requirements:**
- EC2 instance (t2.micro free tier)
- RDS PostgreSQL
- Security groups configured

**Steps:**

1. **Launch EC2 instance**
```bash
# Connect to EC2
ssh -i your-key.pem ubuntu@your-ec2-ip

# Install Java
sudo apt update
sudo apt install openjdk-17-jdk
```

2. **Set up RDS PostgreSQL**
- Create RDS PostgreSQL instance
- Note endpoint and credentials

3. **Deploy application**
```bash
# Upload JAR file
scp -i your-key.pem target/foodlink-api-1.0.0.jar ubuntu@your-ec2-ip:~/

# Run application
java -jar foodlink-api-1.0.0.jar \
  --spring.profiles.active=prod \
  --DB_HOST=your-rds-endpoint \
  --DB_USERNAME=postgres \
  --DB_PASSWORD=your-password \
  --JWT_SECRET=your-secret
```

4. **Set up as systemd service** (optional)
```bash
sudo nano /etc/systemd/system/foodlink.service
```

```ini
[Unit]
Description=FoodLink API
After=network.target

[Service]
Type=simple
User=ubuntu
WorkingDirectory=/home/ubuntu
ExecStart=/usr/bin/java -jar /home/ubuntu/foodlink-api-1.0.0.jar
Restart=on-failure

[Install]
WantedBy=multi-user.target
```

```bash
sudo systemctl enable foodlink
sudo systemctl start foodlink
```

**Cost**: ~$15-20/month (EC2 t2.micro + RDS db.t3.micro)

---

### Option 4: DigitalOcean App Platform

**Steps:**
1. Go to [DigitalOcean App Platform](https://www.digitalocean.com/products/app-platform)
2. Create App → Import from GitHub
3. Add PostgreSQL database
4. Configure environment variables
5. Deploy

**Cost**: $12/month (Basic plan + PostgreSQL)

---

## Environment Variables for Production

Create these in your deployment platform:

```bash
# Database
DB_HOST=your-db-host
DB_PORT=5432
DB_NAME=foodlink_db
DB_USERNAME=your-db-user
DB_PASSWORD=your-db-password

# JWT (IMPORTANT: Generate secure key!)
JWT_SECRET=your-super-secret-jwt-key-at-least-256-bits-long

# CORS (Your frontend URLs)
CORS_ORIGINS=https://yourfrontend.com,https://www.yourfrontend.com

# Profile
SPRING_PROFILES_ACTIVE=prod

# Optional: Elasticsearch (if using)
ELASTICSEARCH_URL=your-elasticsearch-url
ELASTICSEARCH_USERNAME=elastic
ELASTICSEARCH_PASSWORD=your-password

# Optional: AWS S3 (for file uploads)
AWS_S3_BUCKET=foodlink-uploads
AWS_REGION=ap-south-1
AWS_ACCESS_KEY=your-access-key
AWS_SECRET_KEY=your-secret-key
```

---

## Generate Secure JWT Secret

```bash
# Option 1: OpenSSL
openssl rand -base64 64

# Option 2: Online
# Visit: https://www.grc.com/passwords.htm
# Copy "63 random alpha-numeric characters"
```

---

## Pre-Deployment Checklist

- [ ] Set secure JWT_SECRET
- [ ] Configure CORS_ORIGINS with your frontend URL
- [ ] Change database credentials
- [ ] Test all API endpoints
- [ ] Run `mvn clean package` successfully
- [ ] Database migrations tested
- [ ] Environment variables set
- [ ] SSL certificate configured (most platforms auto-provide)

---

## Post-Deployment Steps

1. **Test the API**
```bash
curl https://your-api-url.com/api/v1/public/creators/test
```

2. **Check Swagger UI**
```
https://your-api-url.com/swagger-ui.html
```

3. **Monitor logs** (platform-specific)
```bash
# Heroku
heroku logs --tail

# Railway
# Check dashboard logs

# AWS EC2
sudo journalctl -u foodlink -f
```

4. **Database migrations**
Flyway runs automatically on startup. Check logs for:
```
Flyway: Successfully applied 1 migration
```

---

## Database Backup

### PostgreSQL Backup
```bash
# Backup
pg_dump -h your-db-host -U your-user foodlink_db > backup.sql

# Restore
psql -h your-db-host -U your-user foodlink_db < backup.sql
```

### Railway/Heroku
Most platforms have automatic backups. Check dashboard.

---

## Monitoring & Logging

### Add Spring Boot Actuator (Optional)

Add to `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Endpoints available at:
- `/actuator/health` - Health check
- `/actuator/info` - App info
- `/actuator/metrics` - Metrics

---

## Scaling Considerations

### Vertical Scaling (Single Server)
- Increase server resources (RAM, CPU)
- Railway/Heroku: Upgrade plan
- AWS: Larger instance type

### Horizontal Scaling (Multiple Servers)
When you reach 1000+ concurrent users:
1. Add load balancer (AWS ALB, DigitalOcean LB)
2. Deploy multiple instances
3. Use managed database (RDS, DigitalOcean Managed DB)
4. Add Redis for caching
5. Consider CDN for static assets

---

## CI/CD Pipeline (Future)

### GitHub Actions Example
```yaml
name: Deploy to Production

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Build with Maven
        run: mvn clean package -DskipTests
      - name: Deploy to Railway
        run: railway up
```

---

## Cost Comparison

| Platform | Monthly Cost | Pros | Cons |
|----------|-------------|------|------|
| **Railway** | $0-5 | Easy, free tier, auto-scaling | Limited free hours |
| **Heroku** | $7-16 | Mature, reliable | More expensive |
| **DigitalOcean** | $12-20 | Good performance | Manual setup |
| **AWS** | $15-30 | Most powerful, scalable | Complex |

**Recommendation for MVP**: Start with Railway (free), move to DigitalOcean or AWS later.

---

## Security Best Practices

1. **Never commit secrets**
   - Use `.env` files locally
   - Use platform environment variables in production

2. **Use HTTPS only**
   - Most platforms provide free SSL
   - Force HTTPS in production

3. **Strong JWT secret**
   - At least 64 characters
   - Random alphanumeric

4. **Database security**
   - Use strong passwords
   - Restrict access by IP
   - Regular backups

5. **Rate limiting** (add later)
   - Prevent API abuse
   - Use Spring Cloud Gateway or Nginx

---

## Domain Setup

1. **Buy domain** (Namecheap, GoDaddy, etc.)

2. **Point to your API**
   - Add A record: `api.yourdomain.com` → `your-server-ip`
   - Or CNAME: `api.yourdomain.com` → `your-platform-url.com`

3. **SSL Certificate**
   - Most platforms auto-provide
   - Or use Let's Encrypt

4. **Update CORS**
```bash
CORS_ORIGINS=https://yourdomain.com,https://www.yourdomain.com
```

---

## Troubleshooting Production

**502 Bad Gateway**
- Check if app is running: `curl localhost:8080/actuator/health`
- Check logs for startup errors
- Verify database connection

**Database connection timeout**
- Check DB credentials
- Verify security group/firewall rules
- Test connection: `psql -h host -U user -d database`

**Out of memory**
- Increase JVM heap: `-Xmx512m` or `-Xmx1g`
- Upgrade server resources

**Slow performance**
- Add database indexes
- Enable query caching
- Add Redis for frequent queries
- Optimize N+1 queries

---

## Quick Deploy Script

```bash
#!/bin/bash
# deploy.sh

echo "Building application..."
mvn clean package -DskipTests

echo "Deploying to server..."
scp -i ~/.ssh/your-key.pem \
    target/foodlink-api-1.0.0.jar \
    ubuntu@your-server:/home/ubuntu/

ssh -i ~/.ssh/your-key.pem ubuntu@your-server << 'EOF'
    sudo systemctl stop foodlink
    mv foodlink-api-1.0.0.jar foodlink-api.jar
    sudo systemctl start foodlink
    echo "Deployment complete!"
EOF
```

---

## Recommended: Railway Deployment (Step-by-Step)

1. **Push to GitHub**
```bash
git add .
git commit -m "Ready for deployment"
git push origin main
```

2. **Deploy on Railway**
   - Go to https://railway.app
   - "New Project" → "Deploy from GitHub"
   - Select your repository
   - Click "Add PostgreSQL"
   - Railway auto-detects Spring Boot!

3. **Set Environment Variables**
   - Click "Variables" tab
   - Add:
     - `JWT_SECRET=your-generated-secret`
     - `CORS_ORIGINS=https://your-frontend-url.com`
   - Railway auto-sets database variables

4. **Deploy**
   - Click "Deploy"
   - Wait 2-3 minutes
   - Your API is live at: `your-project.railway.app`

5. **Test**
```bash
curl https://your-project.railway.app/api/v1/public/creators/test
```

**Done! Your API is live!** 🎉

---

Need help? Check platform-specific docs:
- Railway: https://docs.railway.app
- Heroku: https://devcenter.heroku.com
- AWS: https://docs.aws.amazon.com
- DigitalOcean: https://docs.digitalocean.com
