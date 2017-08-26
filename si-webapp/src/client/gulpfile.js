// gulp
var gulp = require('gulp');

// plugins
var connect = require('gulp-connect');
var proxy = require('http-proxy-middleware');
var express = require('express');
var minify = require('gulp-uglify');
var pump = require('pump');
var babel = require('gulp-babel');
var ngAnnotate = require('gulp-ng-annotate');
var jshint = require('gulp-jshint');
var concat = require('gulp-concat');
var useref = require('gulp-useref');
var gulpif = require('gulp-if');
var minifyCss = require('gulp-minify-css');
var runSequence = require('run-sequence');
var templateCache = require('gulp-angular-templatecache');
var del = require('del');
var deleteEmpty = require('delete-empty');
var preprocess = require('gulp-preprocess');


// Checking for errors in scripts
gulp.task('jshint', function () {
    return gulp.src(['.tmp/**/*.js', '!.tmp/**/*.min.js', '!.tmp/bower_components/**'])
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});


gulp.task('copy', function () {
    return gulp.src([
        // copy all
        './src/**'
    ])
        .pipe(gulp.dest('.tmp'))
});

gulp.task('babel', function () {
    return gulp.src([
        '.tmp/**/*.js',
        '!./.tmp/bower_components/lodash/vendor/firebug-lite/src/firebug-lite-debug.js',
        '!./.tmp/bower_components/ng-dialog/**',
        '!./.tmp/bower_components/ngstorage/**'
    ])
        .pipe(babel({
            presets: ['babel-preset-es2015-without-strict'],
            compact: false
        }))
        .on('error', console.error.bind(console))
        .pipe(gulp.dest('.tmp'))
});

gulp.task('html', function () {
    return gulp.src('.tmp/**/*.html')
        .pipe(useref())
        .pipe(gulpif('*.js', ngAnnotate()))
        .pipe(gulpif('*.js', minify()))
        .pipe(gulpif('*.css', minifyCss()))
        .pipe(gulp.dest('dist'));
});

gulp.task('copyData', function () {
    return gulp.src([
        './.tmp/**/*',
        '!.tmp/bower_components/bootstrap/**/',
        '!.tmp/bower_components/components-font-awesome/**/',
        '!./.tmp/**/*.js',
        '!./.tmp/**/*.html',
        '!./.tmp/**/*.css',
        '!./.tmp/**/*.sass',
        '!./.tmp/**/*.less'])
        .pipe(gulp.dest('dist'))
});

gulp.task('copyComponents', function () {
    return gulp.src([
        './.tmp/bower_components/bootstrap/**/*/',
        './.tmp/bower_components/components-font-awesome/**/'
    ], {base: '.tmp'})
        .pipe(gulp.dest('dist'))
});

gulp.task('build-html-template', function () {
    return gulp.src([
        '.tmp/**/*.html',
        '!.tmp/index.html'
    ])
        .pipe(templateCache('templates.js', {module: 'templateCache', standalone: true}))
        .pipe(gulp.dest('.tmp/build'));
});

// This task removes all .html in the specified folder
gulp.task('del', function () {
    return del([
        'dist/**/*.html',
        '!dist/index.html'
    ])
});

gulp.task('preprocessing', function () {
    gulp.src(['./.tmp/**/*.html',
        './.tmp/**/*.js',
        '!.tmp/bower_components/**/'
    ])
        .pipe(preprocess({
            context: {
                templateCache: ' <script src="build/templates.js"></script>'
            }
        }))
        .pipe(gulp.dest('./.tmp'))
});

//This task removes all empty folders in the specified folder
gulp.task('delete-empty-directories', function () {
    deleteEmpty.sync('dist/');
});

gulp.task('start', function (callback) {
    runSequence('copy', 'build-html-template','preprocessing','babel', 'html', 'copyData', 'copyComponents', 'newConnect','del','delete-empty-directories', callback);
});

// to run new server
gulp.task('newConnect', function () {
    connect.server({
        root: 'dist/',
        port: 9000,
        livereload: true,
        middleware: function (connect, opt) {
            return [
                proxy('http://localhost:8080/api')
            ]
        }
    });
});

gulp.task('connect', function () {
    connect.server({
        root: 'src/',
        port: 7000,
        livereload: true,
        middleware: function (connect, opt) {
            return [
                proxy('http://localhost:8080/api')
            ]
        }
    });
});



