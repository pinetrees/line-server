bundle install --path vendor/bundle
echo "We're going to use a database to hash the provided text file."
echo "Enter your mysql username:"
read user
mysql -u $user -p < db.bat
ruby migrate.rb
