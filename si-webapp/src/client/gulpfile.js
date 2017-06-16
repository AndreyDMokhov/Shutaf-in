// gulp
var gulp = require('gulp');

// plugins
var connect = require('gulp-connect');
var proxy = require('http-proxy-middleware');
var express = require('express');


gulp.task('connect', function () {
  connect.server({
    root: 'src/',
      port: 7000,
      livereload: true,
      middleware: function(connect, opt) {
          return [
              proxy('http://localhost:8080/api')
          ]
      }
  });
});