<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%
String modelId = (String)request.getParameter("modelId");
%>
	<div class="modal fade" id="<%=modelId %>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    	<div class="modal-dialog">
        	<div class="modal-content">
        	</div> <!-- /.modal-content -->
		</div> <!-- /.modal-dialog -->
	</div>
	
	<%if(modelId.equals("textResModal")) { %>
	<script>
		jQuery(function($) {
			/* ----------------------------------------------------------- */
			/*  text resources Edit
			/* ----------------------------------------------------------- */
			$('#textResModal').on('hidden.bs.modal', function () {
				$('#textResModal').removeData('bs.modal');
			});
	      
			$('#textResModal').on('loaded.bs.modal', function () {
				var submitButton = $(this).find('#text-resources-submit-button');
				var submitform = $(this).find('#resources-form');
				var resourcesKey = submitform.find('[name=resourcesKey]').val();
	  		
				// submit
				submitButton.bind('click', function(e) {
	  			
					var formData = submitform.serialize();
					var newContent = submitform.find('[name=content]').val();
	  			
					$.ajax({
						type: "POST",
						url: "/spot/secure/editTextResourcesEntry",
						data: formData,
						dataType: "json",
						success: function(data) {
							if(data.status == "OK") {
	  						
								var resourcesTarget = $('#' + resourcesKey);
	  						
								$('#textResModal').modal('hide');
	  						
								resourcesTarget.html(newContent.replace(/\r?\n/g, '<br>'));
								resourcesTarget.css({"display":"none"});
								resourcesTarget.animate({ opacity: 'show'},{ duration: 1500, easing: 'swing'});
							}
						},
						complete: function(data) {
							console.log(data);
							button.attr("disabled", false);
						}
					});
	  			
				});
			});
		});
	</script>
	<%}else if(modelId.equals("itemDeleteModal")) { %>
	<script>
		jQuery(function($) {
			/* ----------------------------------------------------------- */
			/*  item delete
			/* ----------------------------------------------------------- */
			$('#itemDeleteModal').on('hidden.bs.modal', function () {
				$('#itemDeleteModal').removeData('bs.modal');
			});
	      
			$('#itemDeleteModal').on('loaded.bs.modal', function () {
				var submitButton = $(this).find('#item-delete-submit-button');
				var submitform = $(this).find('#item-delete-submit-form');
				var resourcesKey = submitform.find('[name=resourcesKey]').val();
	  		
				// submit
				submitButton.bind('click', function(e) {
					submitform.submit();
				});
			});
		});
	</script>
	<%}else if(modelId.equals("editOfficeHoursModal")) { %>
	<script>
		jQuery(function($) {
			/* ----------------------------------------------------------- */
			/*  item delete
			/* ----------------------------------------------------------- */
			$('#editOfficeHoursModal').on('hidden.bs.modal', function () {
				$('#editOfficeHoursModal').removeData('bs.modal');
			});
	      
			$('#editOfficeHoursModal').on('loaded.bs.modal', function () {
				var openTime = $('#openTime').data('openTime');
				var closeTime = $('#closeTime').data('closeTime');
				
				var submitButton = $(this).find('#office-hours-submit-button');
				var submitform = $(this).find('#office-hours-submit-form');
				
				var openTimeObj = $('#openTime');
				var closeTimeObj = $('#closeTime');
				
				openTimeObj.timepicker({
					template: false,
					showMeridian: false,
					showInputs: false,
					defaultTime: openTime
			    });
				
				closeTimeObj.timepicker({
					template: false,
					showMeridian: false,
					showInputs: false,
					defaultTime: closeTime
			    });
				
				// open flg radio
				var timeArea = $('#time-area');
				
				var openFlgRadio = $('input[name="isOpenFlg"]:radio' );
				
				openFlgRadio.change(function() {
					
					if($( this ).val() == 'true') {
						console.log('is true');
						timeArea.show();
						
					}else {
						console.log('is false');
						timeArea.hide();
						
					}
				});
	  		
				// submit
				submitButton.bind('click', function(e) {
	  			
					var formData = submitform.serialize();
	  			
					$.ajax({
						type: "POST",
						url: "/spot/secure/editOfficeHoursEntry",
						data: formData,
						dataType: "json",
						success: function(data) {
							if(data.status == "OK") {
								
								var openHour = ('0'+ openTimeObj.data('timepicker').hour).slice(-2);
								var openMinute = ('0'+ openTimeObj.data('timepicker').minute).slice(-2);
								var closeHour = ('0'+ closeTimeObj.data('timepicker').hour).slice(-2);
								var closeMinute = ('0'+ closeTimeObj.data('timepicker').minute).slice(-2);
								
								var resourcesTarget = $('#' + submitform.find('[name=targetBoxId]').val());
	  						
								$('#editOfficeHoursModal').modal('hide');
								
								resourcesTarget.find('.office-open-hour').text(openHour);
								resourcesTarget.find('.office-open-minute').text(openMinute);
								resourcesTarget.find('.office-close-hour').text(closeHour);
								resourcesTarget.find('.office-close-minute').text(closeMinute);
								
								resourcesTarget.css({"display":"none"});
								
								if($('input[name="isOpenFlg"]:checked' ).val() == 'true') {
									resourcesTarget.find('.spot-office-hours-area').animate({ opacity: 'show'},{ duration: 1500, easing: 'swing'});
								}else {
									resourcesTarget.find('.spot-office-hours-area').hide();
								}
								
								
								resourcesTarget.animate({ opacity: 'show'},{ duration: 1500, easing: 'swing'});
							}

						},
						complete: function(data) {
						}
					});
	  			
				});
			});
		});
	</script>
	<%} %>