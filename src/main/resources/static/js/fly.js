$('a').click(function(e) {
    e.preventDefault();
    $('img').clone()
                .css({'position': 'absolute', 'z-index': '100'})
                .appendTo('a').animate({
                    top: -180,
                    left: 350,
                    opacity: 0,
                    width: 10,
                    height: 10
                }, 3000, function() {
    });
});
// var btns = document.querySelectorAll('button');
//
// $(btns).on('click', function(){
//
//     id = $(this).attr("data-rel");
//     tmp="#img"+id;
//     console.log(tmp);
//     $(tmp)
//         .clone()
//
//
//         .css({'display' : 'block', 'position' : 'absolute', 'z-index' : '11100', top: $(tmp).offset().top, left:$(tmp).offset().left})
//         .appendTo("body")
//         .animate({opacity: 0.5,
//             left: $(".bt_basket").offset()['left'],
//             top: $(".bt_basket").offset()['top'],
//             width: 0}, 1000, function() {
//             $(this).remove();
//
//         });
//
// });
// $('.add-to-cart').on('click', function () {
//     var cart = $('.shopping-cart');
//     var imgtodrag = $(this).parent('.item-fly').find("img").eq(0);
//     if (imgtodrag) {
//         var imgclone = imgtodrag.clone()
//             .offset({
//                 top: imgtodrag.offset().top,
//                 left: imgtodrag.offset().left
//             })
//             .css({
//                 'opacity': '0.5',
//                 'position': 'absolute',
//                 'height': '150px',
//                 'width': '150px',
//                 'z-index': '100'
//             })
//             .appendTo($('body'))
//             .animate({
//                 'top': cart.offset().top + 10,
//                 'left': cart.offset().left + 10,
//                 'width': 75,
//                 'height': 75
//             }, 1000, 'easeInOutExpo');
//
//         setTimeout(function () {
//             cart.effect("shake", {
//                 times: 2
//             }, 200);
//         }, 1500);
//
//         imgclone.animate({
//             'width': 0,
//             'height': 0
//         }, function () {
//             $(this).detach()
//         });
//     }
// });
// function fly() {
//     $(document).ready(function () {
//         $("#fly").click(function () {
//             $("#target").clone()
//                 .css({'position': 'absolute', 'z-index': '100'})
//                 .appendTo("#fly")
//                 //.appendTo(".image_block")
//                 .animate({
//                     top: -180,
//                     left: 350,
//                     opacity: 0,
//                     width: 10,
//                     height: 10
//                 }, 3000, function () {
//                     $(this).remove();
//                 });
//         });
//     });
// }
// <script type="text/javascript">
//     $(document).ready(function () {
//     $("#fly").click(function (e) {
//         e.preventDefault();
//         //$(".block").clone()
//         $("#target").clone()
//             .css({'position': 'absolute', 'z-index': '100'})
//             .appendTo(fly)
//             //.appendTo(".image_block")
//             .animate({
//                 top: -180,
//                 left: 350,
//                 opacity: 0,
//                 width: 10,
//                 height: 10
//             }, 3000, function () {
//                 $(this).remove();
//             });
//     });
// });
// </script>
// </a>
//
//
// <%--            <script type="text/javascript">--%>
// <%--                $(document).ready(function () {--%>
// <%--                    var fly = document.getElementById("fly+${product.id}");--%>
// <%--                    var target = document.getElementById("target+${product.id}");--%>
// <%--                    $(fly).click(function (e) {--%>
// <%--                        e.preventDefault();--%>
// <%--                        //$(".block").clone()--%>
// <%--                            $(target).clone()--%>
// <%--                            .css({'position': 'absolute', 'z-index': '100'})--%>
// <%--                            .appendTo(fly)--%>
// <%--                            //.appendTo(".image_block")--%>
// <%--                            .animate({--%>
// <%--                            top: -180,--%>
// <%--                            left: 350,--%>
// <%--                            opacity: 0,--%>
// <%--                            width: 10,--%>
// <%--                            height: 10--%>
// <%--                        }, 3000, function () {--%>
// <%--                            $(this).remove();--%>
// <%--                        });--%>
// <%--                    });--%>
// <%--                });--%>
// <%--            </script>--%>
//
//
// <%--            <div style="background: #ccc; padding:10px 5px; height:111px;">--%>
// <%--                <button id="fly"><spring:message code="actionWithProduct.addProduct"/></button><br>--%>
// <%--                <img src="https://stepasyuk.org.ua/wp-content/uploads/2011/07/103345.jpg" id="target" style="margin:5px 0;">--%>
// <%--            </div>--%>
// <%--            <script>--%>
//
// <%--                $('a').click(function (e) {--%>
// <%--                    e.preventDefault();--%>
// <%--                    var href = $(this).attr('href');--%>
// <%--                    $('.img').clone()--%>
// <%--                        .css({'position': 'absolute', 'z-index': '100'})--%>
// <%--                        .appendTo(".image_block").animate({--%>
// <%--                        top: $(".img").offset()['top'],--%>
// <%--                        left: $(".img").offset()['left'],--%>
// <%--                        opacity: 0,--%>
// <%--                        width: 40--%>
// <%--                    }, 2000, function () {--%>
// <%--                        $(this).remove();--%>
// <%--                        $('.block-2').show().animate({--%>
// <%--                            top: 200,--%>
// <%--                            opacity: 1--%>
// <%--                        });--%>
// <%--                        setTimeout(function(){--%>
// <%--                            $('.block-2').fadeOut(500)--%>
// <%--                        }, 2000);--%>
// <%--                    });--%>
// <%--                    $('.block-2 span').click(function(){--%>
// <%--                        $('.block-2').fadeOut(300);--%>
// <%--                        var win = window.open(href, '_self');--%>
// <%--                        if (win)--%>
// <%--                            win.focus();--%>
// <%--                        else--%>
// <%--                            document.location = href;--%>
// <%--                    });--%>
// <%--                });--%>
// <%--            </script>--%>
//
// <%--                --%>
// <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
// <script src='https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js'></script>
// <%--                --%>