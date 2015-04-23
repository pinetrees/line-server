require 'rubygems'
require 'bundler/setup'
require 'sinatra'
require 'active_record'
load 'db.rb'

puts "Running the database loader...feel free to use the API while the data is loaded...this may take some time..."
class Line < ActiveRecord::Base
end

Line.delete_all
lines = File.readlines('file.txt')
lines.each_with_index { |line, index| Line.create(index: index, content: line) }
