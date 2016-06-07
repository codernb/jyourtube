var app = angular.module('yourtube', ['onsen']);

app.run(function () {
  var tag = document.createElement('script');
  tag.src = "https://www.youtube.com/iframe_api";
  var firstScriptTag = document.getElementsByTagName('script')[0];
  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
});

app.config( function ($httpProvider) {
  delete $httpProvider.defaults.headers.common['X-Requested-With'];
});

app.service('VideosService', ['$window', '$rootScope', '$http', function ($window, $rootScope, $http) {

  var service = this;
  var results = [];

  var youtube = {
    ready: false,
    player: null,
    playerId: null,
    videoId: null,
    videoTitle: null,
    playerHeight: '100%',
    playerWidth: '100%',
    state: 'stopped'
  };

  $window.onYouTubeIframeAPIReady = function () {
    youtube.ready = true;
    service.bindPlayer('placeholder');
    service.loadPlayer();
    $rootScope.$apply();
  };

  this.bindPlayer = function (elementId) {
    youtube.playerId = elementId;
  };

  this.createPlayer = function () {
    return new YT.Player(youtube.playerId, {
      height: youtube.playerHeight,
      width: youtube.playerWidth,
      playerVars: {
        rel: 0,
        showinfo: 0
      }
    });
  };

  this.loadPlayer = function () {
    if (youtube.ready && youtube.playerId) {
      if (youtube.player) {
        youtube.player.destroy();
      }
      youtube.player = service.createPlayer();
    }
  };

  this.launchPlayer = function (id) {
    youtube.player.loadVideoById(id);
    youtube.state = 'playing';
    return youtube;
  }

  this.listResults = function (data, append) {
    if (!append)
    	results.length = 0;
    for (var i = data.items.length - 1; i >= 0; i--) 
    	getVideo(data.items[i].id.videoId, i);
  };
  
  function getVideo(id, index) {
      $http.get('https://www.googleapis.com/youtube/v3/videos', {
          params: {
            key: 'AIzaSyCDx2qUdt0KTmNqSjErNeiYrx1tr6xVc6Q',
            id: id,
            part: 'snippet, status',
            fields: 'items/id, items/snippet/title, items/snippet/description, items/snippet/thumbnails/default, items/snippet/channelTitle, items/status/embeddable',
            q: this.query
          }
        })
        .success( function (data) {
          if (data.items[0].status.embeddable)
            results[index] = {
              id: data.items[0].id,
              title: data.items[0].snippet.title,
              description: data.items[0].snippet.description,
              thumbnail: data.items[0].snippet.thumbnails.default.url,
              author: data.items[0].snippet.channelTitle
            };
        });
  }

  this.getYoutube = function () {
    return youtube;
  };

  this.getResults = function () {
    return results;
  };

}]);

app.controller('MainController', function ($scope, $http, $interval, VideosService) {

    $scope.youtube = VideosService.getYoutube();
    
    $scope.next = function() {
        $http.get('/video/next').success(function (data) {
            $scope.launch(data.id);
        });
    };
    
    $scope.clear = function() {
        $http.delete('/request/all').success(function (data) {
            $scope.update();
        });
    };
    
    $scope.launch = function(videoId) {
      VideosService.launchPlayer(videoId);
    };
    
    $scope.remove = function(video) {
        $http.delete('/request/' + video.id).success(function (data) {
            $scope.update();
        });
    }
    
    $scope.update = function() {
    	$http.get('/video/current').success(function (data) {
    		$scope.current = data;
    	});
        $http.get('/video/requested').success(function (data) {
            $scope.videos = data;
        });
    }
    
    $interval(function(){
        $http.get('/version').success(function (data) {
            if ($scope.version === data)
                return;
            $scope.version = data;
            $scope.update();
        });
        
        $http.get('/volume').success (function (data) {
            if ($scope.youtube.player.getVolume() !== data)
                $scope.youtube.player.setVolume(data);
        });
        
        var state = $scope.youtube.player.getPlayerState();
        if (!$scope.youtube.ready || (state !== -1 && state !== 5 && state !== 0))
            return;
        $scope.next();
    }, 1000);
    
});

app.controller('UIController', function ($scope, $http, $interval, VideosService) {
	
	$scope.videoOrder = 'title';
	$scope.descending = false;

    $scope.results = VideosService.getResults();
    
    $scope.setOrder = function(order) {
    	if ($scope.videoOrder === order)
    		$scope.descending = !$scope.descending;
    	else
    		$scope.videoOrder = order;
    }
    
    $scope.update = function() {
        $http.get('/video/current').success(function (data) {
            $scope.current = data;
        });
        $http.get('/video/requested').success(function (data) {
            $scope.videos = data;
        });
        $http.get('/video').success(function (data) {
            $scope.allVideos = data;
        });
    };
    
    $scope.enterVideo = function (video) {
        $http.post('/video', video).success(function (data) {
            $scope.update();
        });
    };
    
    $scope.volumeUp = function () {
        $http.post('/volume/up');
    };
    
    $scope.volumeDown = function () {
        $http.post('/volume/down');
    };
    
    $interval(function(){
        $http.get('/version').success(function (data) {
            if ($scope.version === data)
                return;
            $scope.version = data;
            $scope.update();
        });
        if ($scope.searchRequested) {
        	$scope.searchRequested = false;
        	$scope.search(true);
        }
    }, 1000);
    
    $scope.search = function (isNewQuery) {
      $scope.query = this.query;
      if ($scope.loading) {
    	  $scope.searchRequested = true;
    	  return;
      }
      $scope.loading = true;
      $http.get('https://www.googleapis.com/youtube/v3/search', {
        params: {
          key: 'AIzaSyCDx2qUdt0KTmNqSjErNeiYrx1tr6xVc6Q',
          type: 'video',
          maxResults: '10',
          pageToken: isNewQuery ? '' : $scope.nextPageToken,
          part: 'id',
          fields: 'items/id, nextPageToken',
          q: $scope.query
        }
      })
      .success( function (data) {
        if (data.items.length === 0)
          $scope.label = 'No results were found!';
        VideosService.listResults(data, $scope.nextPageToken && !isNewQuery);
        $scope.nextPageToken = data.nextPageToken;
      })
      .finally( function () {
        $scope.loadMoreButton.stopSpin();
        $scope.loadMoreButton.setDisabled(false);
        $scope.loading = false;
      });
    };
});
