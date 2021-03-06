/**
 * Created by ASUA on 2015/11/20.
 */
$(document).ready(function() {
    var email = $("#email").text();
    var username = $('#username').text();

    function generateComment(comment) {
        return '<li class="list-group-item">' +
            '<p><strong>' + username + '</strong>: ' + comment + '</p>' +
            '<p class="text-right small"><span class="glyphicon glyphicon-time"></span> ' + new Date().toDateString() + '</p>' +
            '</li>';
    }

    $('#btnToggle').click(function(){
        if ($(this).hasClass('on')) {
            $('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
            $(this).removeClass('on');
        }
        else {
            $('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
            $(this).addClass('on');
        }
    });
    $('#my-follower,.btn-follower').click(function() {
        var $modal = $('#followerModel');
        $modal.modal('show');
        $modal.find(".modal-body .row").html("");
        console.log($(this).attr("data-id"));
        $.get("/follower/" + $(this).attr("data-id"), function(data) {
            var html = "",
                i,
                length;
            for (i = 0, length = data.length; i < length; i++) {
                html += '<div class="col-xs-4">\n<div class="col-xs-6">\n<img alt="Bootstrap Image Preview" src="http://lorempixel.com/140/140/" class="img-responsive" />\n</div>\n<div class="col-xs-6">\n<h2>' +
                    data[i].lastName + " " + data[i].firstName +'</h2>\n<p>Email:'
                    + data[i].email + '</p>\n</div>\n</div>';
            }
            $modal.find(".modal-body .row").html(html);
        });
    });
    $(".btn-follow,.glyphicon-plus").click(function(event) {
        event.stopPropagation();
        event.preventDefault();
        $.post("/follow", {
            follower: email,
            followee: $(this).attr('data-email')
        }).success(function () {
            $('.btn-follow[data-email="' + $(this).attr('data-email') + '"]').removeClass("btn-default").addClass("btn-success disabled");
        }.bind(this));
        return false;
    });
    $(".btn-like,.glyphicon-heart-empty").click(function(event) {

        $.post("/post/like", {
            postId: $(this).attr('data-pid'),
            email: email
        }).success(function () {
            $('.btn-like[data-pid="' + $(this).attr('data-pid') + '"]').removeClass("btn-default").addClass("btn-danger");
        }.bind(this));
        return false;
    });
    var $editedPost;
    $(".a-edit").click(function(event) {
        $editedPost = $(event.target).closest(".panel");
        $('#editModal').find("form").attr("data-pid", $(event.target).attr("data-pid")).end().modal('show')
    });
    $(".a-delete").click(function(event) {
        $editedPost = $(event.target).closest(".panel");
        $.get('/post/delete/' + $(event.target).attr("data-pid")).success(function() {
            $editedPost.remove();
        });
    });
    $('#edit-form').submit(function(event) {
        var newContent = $(this).find('input').val();
        $.post("/post/edit", {
            postId: $(this).attr('data-pid'),
            newContent: $(this).find('input').val()
        }).success(function () {
            $('#editModal').modal('hide');
            $editedPost.find("p.content").text(newContent);
        }.bind(this));
        return false;
    });

    $("input[type='checkbox']").bootstrapSwitch();

    navigator.geolocation.getCurrentPosition(function(position) {
        $("input[name='latitude']").val(Math.round(position.coords.latitude * 100) / 100);
        $("input[name='longitude']").val(Math.round(position.coords.longitude * 100) / 100);
        var apiKey = 'b04dbf475994a98f5849aa6856a4596d';
        var curl = 'https://api.forecast.io/forecast/';
        //var lati = 40.7146;
        //var longi = -74.0071;
        var lati = Math.round(position.coords.latitude * 100) / 100;
        var longi = Math.round(position.coords.longitude * 100) / 100;
        console.log("lati=",lati," longi=",longi);
        var data;
        $.ajax({
            type: "GET",
            url: curl + apiKey +"/"+ lati +","+ longi +"?callback=?",
            dataType: "json",
            success: function(data){
                //var json = $.parseJSON(data);
                console.log(data);
                $("#currentTemperature").append(Math.floor(data.currently.apparentTemperature));
                $("#currentTime").append(Date(data.currently.time));
                if(data.currently.icon == 'clear-day') { $("#currentIcon").addClass("wi-day-sunny") };
                if(data.currently.icon == 'clear-night') { $("#currentIcon").addClass("wi-night-clear") };
                if(data.currently.icon == 'rain') { $("#currentIcon").addClass("wi-rain") };
                if(data.currently.icon == 'snow') { $("#currentIcon").addClass("wi-snow") };
                if(data.currently.icon == 'sleet') { $("#currentIcon").addClass("wi-day-sleet") };
                if(data.currently.icon == 'wind') { $("#currentIcon").addClass("wi-day-windy") };
                if(data.currently.icon == 'fog') { $("#currentIcon").addClass("wi-fog") };
                if(data.currently.icon == 'cloudy') { $("#currentIcon").addClass("wi-cloudy") };
                if(data.currently.icon == 'partly-cloudy-day') { $("#currentIcon").addClass("wi-day-cloudy") };
                if(data.currently.icon == 'partly-cloudy-night') { $("#currentIcon").addClass("wi-night-cloudy") };
                if(data.currently.icon == 'hail') { $("#currentIcon").addClass("wi-hail") };
                if(data.currently.icon == 'thunderstorm') { $("#currentIcon").addClass("wi-thunderstorm") };
                if(data.currently.icon == 'tornado') { $("#currentIcon").addClass("wi-meteor") };
                $("#humidity").append(data.currently.humidity);
                $("#currentWind").append(data.currently.windSpeed);
                $("#hourlySummary").append(data.hourly.summary);
                $("#currentSummary").append(data.currently.summary);
                $("#daily_summary").append(data.daily.summary);
                $("weekly").append(data.daily.data[0].apparentTemperatureMax);
            },
            error: function() {
                alert("An error occurred");
            }
        });
    });

    $(".btn-share").click(function (event) {

        var $sharedPost = $(event.target).closest(".panel").clone();
        $.post('/share', {
            postId: $(event.target).attr("data-pid"),
            email: email
        }).success(function () {
            $sharedPost.prependTo($('#shared-post')).hide().slideDown('slow');
        });
        return false;
    });

    //$("#search-options").hide();
    //$("#search-exact").hide();
    //$("#srch-term").focus(function () {
    //    $("#search-options").slideDown();
    //});
    //$(".btn-form-close").click(function () {
    //    $("#search-options").slideUp();
    //    $("#search-fuzzy-form").slideDown();
    //    $("#search-exact").hide();
    //    $('#search-type').bootstrapSwitch('state', true, true);
    //});
    //$('#search-type').on('switchChange.bootstrapSwitch', function(event, state) {
    //    $("#search-exact").slideToggle();
    //    $("#search-fuzzy-form").slideToggle();
    //});

    $('#soc-lastname').autocomplete({
        serviceUrl: '/user/autocomplete/lastname'
    });
    $('#soc-firstname').autocomplete({
        serviceUrl: '/user/autocomplete/firstname'
    });
    $('#soc-email').autocomplete({
        serviceUrl: '/user/autocomplete/email'
    });

    $('.new-comments').submit(function (event) {
        $comments = $(this).parent().next();
        $this = $(this);
        $.post("/comment", {
            email: email,
            postId: $this.attr("data-pid"),
            comment: $this.find("input[type='text']").val()
        }).success(function () {
            $(generateComment($this.find("input").val())).prependTo($comments).hide().slideDown('slow');
            $this.find("input[type='text']").val("");
        });
        return false;
    });

    $('.post-item').each(function (index, element){
        $.get("/comment/post/" + $(element).attr('data-id'))
            .success(function (data) {
                var $element = $(element);
                var i = 0,
                    length = data.length,
                    comments = "",
                    item;
                for (i = 0; i < length; i++) {
                    item = data[i];
                    comments += '<li class="list-group-item">' +
                        '<p><strong>' + item.commenter.userName + '</strong>: ' + item.comment + '</p>' +
                        '<p class="text-right small"><span class="glyphicon glyphicon-time"></span>' + new Date(item.createTime).toDateString()  + '</p></li>';
                }

                $(comments).appendTo($element.find(".comment-list")).hide().slideDown();
            });
    });

});