$(function () {

  'use strict';

  var console = window.console || { log: function () {} },
      $alert = $('.docs-alert'),
      $message = $alert.find('.message'),
      showMessage = function (message, type) {
        $message.text(message);

        if (type) {
          $message.addClass(type);
        }

        $alert.fadeIn();

        setTimeout(function () {
          $alert.fadeOut();
        }, 3000);
      };

  // Demo
  // -------------------------------------------------------------------------

  (function () {
    var $image = $('.img-container > img'),
        $dataX = $('#imageX'),
        $dataY = $('#imageY'),
        $dataHeight = $('#imageHeight'),
        $dataWidth = $('#imageWidth'),
        $dataRotate = $('#imageRotate'),
        options = {

                // modal: false,
                // guides: false,
                 center: false,
                // highlight: false,
                 background: false,

                 // autoCrop: false,
                 autoCropArea: 1.0,
                 dragCrop: false,
                 // movable: false,
                 // rotatable: false,
                 // zoomable: false,
                 // touchDragZoom: false,
                 // mouseWheelZoom: false,
                 // cropBoxMovable: false,
                 // cropBoxResizable: false,
                 // doubleClickToggle: false,

                // minCanvasWidth: 320,
                // minCanvasHeight: 180,
                // minCropBoxWidth: 750,
                // minCropBoxHeight: 90,
                // minContainerWidth: 750,
                // minContainerHeight: 180,

                // build: null,
                // built: null,
                // dragstart: null,
                // dragmove: null,
                // dragend: null,
                // zoomin: null,
                // zoomout: null,

                // aspectRatio: 10 / 10,
                preview: '.img-preview',
                crop: function (data) {
                  $dataX.val(Math.round(data.x));
                  $dataY.val(Math.round(data.y));
                  $dataHeight.val(Math.round(data.height));
                  $dataWidth.val(Math.round(data.width));
                  $dataRotate.val(Math.round(data.rotate));
                  
                  $('#displayWidth').val($dataWidth.val());
                  $('#displayHeight').val($dataHeight.val());
                }
              };

    $image.on({
//      'build.cropper': function (e) {
//        console.log(e.type);
//      },
//      'built.cropper': function (e) {
//        console.log(e.type);
//        $(this).cropper('disable');
//        $('#crop-btn-group').css('display', 'inline-block');
//      }
//      'dragstart.cropper': function (e) {
//        console.log(e.type, e.dragType);
//      },
//      'dragmove.cropper': function (e) {
//        console.log(e.type, e.dragType);
//      },
//      'dragend.cropper': function (e) {
//        console.log(e.type, e.dragType);
//      },
//      'zoomin.cropper': function (e) {
//        console.log(e.type);
//      },
//      'zoomout.cropper': function (e) {
//        console.log(e.type);
//      },
//      'change.cropper': function (e) {
//        console.log(e.type);
//      }
    }).cropper(options);
    
//    $('#enable-button').click(function () {
//    	$image.cropper('enable');
//    	$('#enable-button').hide();
//    	$('#clear-button').show();
//    });
    
//    $('#clear-button').click(function () {
//    	$image.cropper('reset');
//    	$image.cropper('disable');
//    	$('#enable-button').show();
//    	$('#clear-button').hide();
//    });


    // Import image
    var $inputImage = $('#inputImage'),
        URL = window.URL || window.webkitURL,
        blobURL;
    

    if (URL) {
      $inputImage.change(function () {
    	  
        var files = this.files,
            file;

        if (!$image.data('cropper')) {
          return;
        }

        if (files && files.length) {
          file = files[0];

          if (/^image\/\w+$/.test(file.type)) {
            blobURL = URL.createObjectURL(file);
            $image.one('built.cropper', function () {
              URL.revokeObjectURL(blobURL); // Revoke when load complete
            }).cropper('reset').cropper('replace', blobURL);
//            $inputImage.val('');
          } else {
            showMessage('Please choose an image file.');
          }
        }
      });
    } else {
      $inputImage.parent().remove();
    }

  }());

});
