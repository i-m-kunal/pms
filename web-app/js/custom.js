
var myApp=angular.module('pms',['ngRoute']);

myApp.config(function ($routeProvider) {
    $routeProvider

        .when('/', {
            templateUrl: 'pms/content.html',
            controller: 'HomeCtrl'
        })

});

myApp.controller('HomeCtrl',function(){

    console.log("Main COntroller Called");

})



