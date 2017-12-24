'use strict';
// gulp

var gulp = require('gulp');

// plugins
var connect = require('gulp-connect');
var proxy = require('http-proxy-middleware');
var express = require('express');
var minify = require('gulp-uglify');
var babel = require('gulp-babel');
var ngAnnotate = require('gulp-ng-annotate');
var useref = require('gulp-useref');
var gulpif = require('gulp-if');
var minifyCss = require('gulp-minify-css');
var runSequence = require('run-sequence');
var templateCache = require('gulp-angular-templatecache');
var deleteFiles = require('del');
var deleteEmpty = require('delete-empty');
var preprocess = require('gulp-preprocess');
var clean = require('gulp-clean');
const eslint = require('gulp-eslint');
var htmlmin = require('gulp-htmlmin');
var livereload = require('gulp-livereload');
var watch = require('gulp-watch');
var less = require('gulp-less');
var path = require('path');


gulp.task('watch', function() {
     gulp.watch(['src/**/*', '!src/bower_components/**'], ['eslint' , 'less']);
});

//clean all,
gulp.task('clean-all', [
    'clean-tmp',
    'clean-dist'
]);

//clean .tmp
gulp.task('clean-tmp', function () {
    return gulp.src('.tmp', {read: false})
        .pipe(clean({force: true}));
});

//clean dist
gulp.task('clean-dist', function () {
    return gulp.src('dist', {read: false})
        .pipe(clean({force: true}));
});

// copy all from src to .tmp
gulp.task('copy', function () {
    return gulp.src([
        // copy all
        './src/**'
    ])
        .pipe(gulp.dest('.tmp'));
});

// compiler from ES6 to ES5
gulp.task('babel', function () {
    return gulp.src([
        '.tmp/**/*.js',
        '!./.tmp/bower_components/lodash/vendor/firebug-lite/src/firebug-lite-debug.js',
        '!./.tmp/bower_components/ng-dialog/**',
        '!./.tmp/bower_components/ngstorage/**',
        '!./.tmp/bower_components/angularjs-slider/**'
    ])
        .pipe(babel({
            presets: ['babel-preset-es2015-without-strict'],
            compact: false
        }))
        .on('error', console.error.bind(console))
        .pipe(gulp.dest('.tmp'));
});
// minifies js and css
gulp.task('minify', function () {
    return gulp.src(['.tmp/**/*.html',
        '!.tmp/bower_components/angularjs-slider/**'])
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
        '!./.tmp/**/*.less',
        '!.tmp/bower_components/angularjs-slider/**/'])
        .pipe(gulp.dest('dist'));
});

gulp.task('copyComponents', function () {
    return gulp.src([
        './.tmp/bower_components/bootstrap/**/*/',
        './.tmp/bower_components/components-font-awesome/**/',
        './.tmp/bower_components/angularjs-slider/**/'
    ], {base: '.tmp'})
        .pipe(gulp.dest('dist'));
});
//builds one js-file from specified html
gulp.task('build-html-template', function () {
    return gulp.src([
        '.tmp/**/*.html',
        '!.tmp/index.html',
        '!.tmp/bower_components/angularjs-slider/**'
    ])
        .pipe(templateCache('templates.js', {module: 'templateCache', standalone: true}))
        .pipe(gulp.dest('.tmp/build'));
});

// This task removes all .html in the specified folder
gulp.task('delete-html', function () {
    return deleteFiles([
        'dist/**/*.html',
        '!dist/index.html',
        '!.tmp/bower_components/angularjs-slider/**'
    ]);
});

// inserts script in specified place and remove comments
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
        .pipe(gulp.dest('./.tmp'));
});

//This task removes all empty folders in the specified folder
gulp.task('delete-empty-directories', function () {
    deleteEmpty.sync('dist/');
});
// minifies the style in accordance with the rules
gulp.task('minifyHtml', function () {
    return gulp.src(['.tmp/**/*.html', '!.tmp/bower_components/**', '!.tmp/index.html',''])
        .pipe(htmlmin({
            collapseWhitespace: true
        }))
        .pipe(gulp.dest('.tmp'));
});


// checks the style in accordance with the rules in .eslintrc
// used for task Build
gulp.task('eslintForBuild', function () {
    return gulp.src(['src/**/*.js', '!node_modules/**', '!src/bower_components/**'])
        .pipe(eslint({
            useEslintrc: true
        }))
        .pipe(eslint.format('table'))
        .pipe(eslint.failAfterError());
});

// checks the style in accordance with the rules in .eslintrc
// used for runtime checking
gulp.task('eslint', function () {
    return gulp.src(['src/**/*.js', '!node_modules/**', '!src/bower_components/**'])
        .pipe(eslint({
            useEslintrc: true
        }))
        .pipe(eslint.format('table'))
        .pipe(eslint.failAfterError())
        .pipe(livereload({start: true}));
});

gulp.task('less', function () {
    return gulp.src(['./src/styles/*.less' ,  '!./src/styles/variables.less'])
        .pipe(less())
        .pipe(gulp.dest('./src/styles'));
});1


// to run new server
gulp.task('minifiedConnect', function () {
    connect.server({
        // root: '.tmp/',
        root: 'dist/',
        port: 9000,
        livereload: true,
        middleware: function (connect, opt) {
            return [
                proxy('http://localhost:8080/api')
            ];
        }
    });
});

gulp.task('runServer', function () {
    connect.server({
        root: 'src/',
        port: 7000,
        livereload: true,
        middleware: function (connect, opt) {
            return [
                proxy('http://localhost:8080/api')
            ];
        }
    });
});

gulp.task('start', ['eslint', 'runServer', 'less', 'watch']);

gulp.task('build', function (callback) {
    runSequence(
        'clean-all',
        'eslintForBuild',
        'copy',
        'minifyHtml',
        'build-html-template',
        'preprocessing',
        'babel',
        'minify',
        'copyData',
        'copyComponents',
        'delete-html',
        'delete-empty-directories',
        callback);
});