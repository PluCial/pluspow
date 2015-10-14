/*
  * @package Spike
  * @subpackage Spike HTML
  * 
  * Template Scripts
  * Created by Tripples
  
   1.    Style Switcher
   2.    Navigation
   3.    Fixed Header
   4.    Main Slideshow (Carousel)
   5.    Counter
   6.    Owl Carousel
   7.    Flex Slider
   8.    Wow Animation
   9.    Contact Map
   10.   Video Background
   11.   Back To Top

  
*/


jQuery(function($) {
  "use strict";


   /* ----------------------------------------------------------- */
   /*  Style Switcher
   /* ----------------------------------------------------------- */

    (function($) { "use strict";
       $(document).ready(function(){
           $('.style-switch-button').click(function(){
        	   $('.style-switch-wrapper').toggleClass('active');
           });
           $('a.close-styler').click(function(){
        	   $('.style-switch-wrapper').removeClass('active');
           });
      });
    })(jQuery);



   /* ----------------------------------------------------------- */
   /*  Fixed header
   /* ----------------------------------------------------------- */

   $(window).on('scroll', function(){

        if( $(window).scrollTop()>100 ){

        $('.header').addClass('header-solid animated fadeInDown');
        } 
        else {

        $('.header').removeClass('header-solid animated fadeInDown');

        }

    }); 

   $(window).on('scroll', function(){

        if( $(window).scrollTop()>200 ){

        $('.header2').addClass('header-bgnone animated fadeInDown');
        } 
        else {

        $('.header2').removeClass('header-bgnone animated fadeInDown');

        }

    }); 


   
  /* ----------------------------------------------------------- */
  /*  Main slideshow
  /* ----------------------------------------------------------- */

   $('#main-slide').carousel({
      pause: true,
      interval: 100000,
   });


   /* ----------------------------------------------------------- */
   /*  Counter
   /* ----------------------------------------------------------- */

      $('.counter').counterUp({
       delay: 10,
       time: 1000
      });



  /* ----------------------------------------------------------- */
  /*  Owl Carousel
  /* ----------------------------------------------------------- */


    //Testimonial

    $("#testimonial-carousel").owlCarousel({
 
      navigation : false, // Show next and prev buttons
      slideSpeed : 600,
      pagination:true,
      singleItem:true
 
    });

    // Custom Navigation Events
    var owl = $("#testimonial-carousel");


    // Custom Navigation Events
    $(".next").click(function(){
      owl.trigger('owl.next');
    })
    $(".prev").click(function(){
      owl.trigger('owl.prev');
    })
    $(".play").click(function(){
      owl.trigger('owl.play',1000); //owl.play event accept autoPlay speed as second parameter
    })
    $(".stop").click(function(){
      owl.trigger('owl.stop');
    })
    

   //Clients

   $("#client-carousel").owlCarousel({

      navigation : false, // Show next and prev buttons
      slideSpeed : 400,
      pagination:false,
      items : 5,
      rewindNav: true,
      itemsDesktop : [1199,3],
      itemsDesktopSmall : [979,3],
      stopOnHover:true,
      autoPlay:true

   });

   //App gallery
   $("#app-gallery-carousel").owlCarousel({

     navigation : false, // Show next and prev buttons
      slideSpeed : 400,
      pagination:true,
      items : 4,
      rewindNav: true,
      itemsDesktop : [1199,3],
      itemsDesktopSmall : [979,3],
      stopOnHover:true
   });



   /* ----------------------------------------------------------- */
   /*  Flex slider
   /* ----------------------------------------------------------- */

      //Second item slider
      $(window).load(function() {
        $('.flexSlideshow').flexslider({
           animation: "fade",
           controlNav: false,
           directionNav: true ,
           slideshowSpeed: 8000
        });
      });


     //Portfolio item slider
      $(window).load(function() {
        $('.flexportfolio').flexslider({
           animation: "fade",
           controlNav: false,
           directionNav: true ,
           slideshowSpeed: 8000
        });
      });

     
   /* ----------------------------------------------------------- */
   /*  Animation
   /* ----------------------------------------------------------- */
        //Wow
        new WOW().init();


   /* ----------------------------------------------------------- */
   /*  Prettyphoto
   /* ----------------------------------------------------------- */

        $("a[data-rel^='prettyPhoto']").prettyPhoto();


   /* ----------------------------------------------------------- */
   /* Video background
   /* ----------------------------------------------------------- */

    var resizeVideoBackground = function() {

        $( '.video-background' ).each(function( i, el ) {
          var $el       = $( el ),
              $section  = $el.parent(),
              min_w     = 300,
              video_w   = 16,
              video_h   = 9,
              section_w = $section.outerWidth(),
              section_h = $section.outerHeight(),
              scale_w   = section_w / video_w,
              scale_h   = section_h / video_h,
              scale     = scale_w > scale_h ? scale_w : scale_h,
              new_video_w, new_video_h, offet_top, offet_left;


          if ( scale * video_w < min_w ) {
            scale = min_w / video_w;
          };

          new_video_w = scale * video_w;
          new_video_h = scale * video_h;
          offet_left = ( new_video_w - section_w ) / 2 * -1;
          offet_top  = ( new_video_h - section_h ) / 2 * -1;

          $el.css( 'width', new_video_w );
          $el.css( 'height', new_video_h );
          $el.css( 'marginTop', offet_top );
          $el.css( 'marginLeft', offet_left );
        });

    };

    $( window ).on( 'resize', function() {
        resizeVideoBackground();
    });
    
    resizeVideoBackground();

   /* ----------------------------------------------------------- */
   /*  Back to top
   /* ----------------------------------------------------------- */

       $(window).scroll(function () {
            if ($(this).scrollTop() > 50) {
                $('#back-to-top').fadeIn();
            } else {
                $('#back-to-top').fadeOut();
            }
        });
      // scroll body to 0px on click
      $('#back-to-top').click(function () {
          $('#back-to-top').tooltip('hide');
          $('body,html').animate({
              scrollTop: 0
          }, 800);
          return false;
      });
      
      $('#back-to-top').tooltip('hide');
      
      
      /* ----------------------------------------------------------- */
      /*  Sort Item
      /* ----------------------------------------------------------- */
      $(".connectedSortable").sortable({
          tolerance: 'pointer',
          helper: 'clone',
          revert: 'invalid',
          placeholder: 'col-sm-4 sort-highlight',
          appendTo: 'body',
          forceHelperSize: true,
          handle: ".sort-bar",
          cursor: 'move',
          update: function(event, ui) {
        	  var sortMaxOrder = ui.item.data('sortMaxOrder');
        	  var spotId = ui.item.data('spotId');
        	  var itemId = ui.item.data("itemId");
        	  var fromOrder = ui.item.data('order');
        	  var toOrder = ui.item.index();
        	  var prevId = ui.item.prev().data("itemId");
        	  var nextId = ui.item.next().data("itemId");
        	  
        	  
        	  console.log('maxIndex: ' + sortMaxOrder);
        	  console.log('id: ' + itemId);
        	  console.log('from: '+ fromOrder);
              console.log('to: '+ toOrder);
              console.log('prevId:' + prevId);
        	  console.log('next:' + nextId);
        	  
        	  if(!itemId || toOrder + 1 > sortMaxOrder) {
        		  $(".connectedSortable").sortable( "cancel" );
        		  return;
        	  }
        	  
        	  var changeUrl = "/spot/secure/itemChangeIndex?spotId=" + spotId + "&itemId=" + itemId;
        	  if(prevId) {
        		  changeUrl = changeUrl + "&prevId=" + prevId;
        	  }
        	  if(nextId) {
        		  changeUrl = changeUrl + "&nextId=" + nextId;
        	  }
        	  
        	  waitingDialog.show();
        	  
        	  $.ajax({
    			  type: "GET",
    			  url: changeUrl,
    			  dataType: "json",
    			  success: function(data) {
    				  if(data.status != "OK") {
    					  $(".connectedSortable").sortable( "cancel" );
    	        		  return;
    				  }
    			  },
    			  error: function(data) {
    				  $(".connectedSortable").sortable( "cancel" );
    				  return;
    			  },
    			  complete: function(data) {
    				  waitingDialog.hide();
            		  return;
    			  }
    		  });
          }
      });
      $(".sortable").disableSelection();
      $(".connectedSortable .effect-oscar").css("cursor", "move");
      
      /* ----------------------------------------------------------- */
      /*  read more
      /* ----------------------------------------------------------- */
      function nextLinkHandler(event) {

    	  var nextUrl = $(this).attr('href');
    	  waitingDialog.show();
    	  
    	  $.ajax({
    		  type: 'GET',
    		  url: nextUrl,
    		  dataType: 'html',
    		  success: function(data) {
    			  $('.listHasNext').remove();
    			  $('.item-list-row').append(data);
    			  
    			  $('a.nextLink').bind('click', nextLinkHandler);
    		  },
			  complete: function(data) {
				  waitingDialog.hide();
        		  return false;
			  }
    	  });
    	  
    	  return false;
      }

      $('a.nextLink').bind('click', nextLinkHandler);
      
      /* ----------------------------------------------------------- */
      /*  langs select
      /* ----------------------------------------------------------- */
      $("#langs-select").change(function() {
    	  var pathname = location.pathname;
    	  var langpath = pathname.split('/')[2];
    	  var selectedval = $(this).val();

    	  var targetpath = pathname.replace('/' + langpath + '/', '/l-' + selectedval + '/');
    	  location.href = targetpath;
      });
      
      /* ----------------------------------------------------------- */
      /*  search more
      /* ----------------------------------------------------------- */
      function nextResultsHandler(event) {
    	  event.preventDefault();
    	  var $form = $(this);
    	  
    	  var $button = $form.find('button');
    	  
    	  $.ajax({
              url: $form.attr('action'),
              type: $form.attr('method'),
              dataType: 'html',
              data: $form.serialize(),
              timeout: 10000,  // 単位はミリ秒
              
              // 送信前
              beforeSend: function(data) {
                  // ボタンを無効化し、二重送信を防止
                  $button.attr('disabled', true);
                  waitingDialog.show();
              },
              // 応答後
              complete: function(data) {
                  // ボタンを有効化し、再送信を許可
                  $button.attr('disabled', false);
                  waitingDialog.hide();
              },
              
              // 通信成功時の処理
              success: function(data) {
            	  $form.remove();
            	  $('.results-row').append(data);
            	  
            	  $('form.nextResultsForm').bind('submit', nextResultsHandler);
              }
          });
      }
      $('form.nextResultsForm').bind('submit', nextResultsHandler);

});