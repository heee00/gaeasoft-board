$(document).ready(function() {
    var currentPage = 1;

    $('#searchAddressButton').on('click', function() {
        openModal();
        searchAddresses(1); // Search from the first page
    });

    function searchAddresses(page) {
        var keyword = $('#address').val().trim();
        if (keyword.length > 0) {
            $.ajax({
                url: '/address/api',
                type: 'post',
                dataType: 'json',
                data: {
                    resultType: 'json',
                    confmKey: 'devU01TX0FVVEgyMDI0MDYxMjE3MDc0ODExNDgzODI=',
                    keyword: keyword,
                    currentPage: page,
                    countPerPage: 10
                },
                success: function(jsonStr) {
                    currentPage = page;
                    displayAddressList(jsonStr.results.juso, jsonStr.results.common.totalCount);
                },
                error: function(xhr, status, error) {
                    alert('주소 검색 실패: ' + error);
                }
            });
        } else {
            alert('주소 검색어를 입력해주세요.');
        }
    }

    function displayAddressList(addresses, totalCount) {
        var modalAddressList = $('#modalAddressList');
        modalAddressList.empty();
        var htmlStr = '<table class="board-table"><tr><th>우편번호</th><th>주소</th></tr>';
        $(addresses).each(function(index, address) {
            htmlStr += '<tr><td>' + address.zipNo + '</td><td><a href="#" class="addressItem" data-zip="' + address.zipNo + '" data-road="' + address.roadAddr + '" data-jibun="' + address.jibunAddr + '">도로명: ' + address.roadAddr + '<br>지번명: ' + address.jibunAddr + '</a></td></tr>';
        });
        htmlStr += '</table>';
        modalAddressList.html(htmlStr);

        // Address list item click event handler
        $('.addressItem').on('click', function(e) {
            e.preventDefault();
            var roadAddr = $(this).data('road');
            var jibunAddr = $(this).data('jibun');

            $('#address').val(roadAddr); // Set the road address in the input field

            $('#roadAddr').val(roadAddr);
            $('#jibunAddr').val(jibunAddr);

            modalAddressList.empty();
            closeModal();
        });

        // Pagination handling
        displayPagination(totalCount);
    }

    function displayPagination(totalCount) {
        var modalAddressList = $('#modalAddressList');
        var pageCount = Math.ceil(totalCount / 10); // Calculate number of pages
        var htmlStr = '<div class="paging-navigation">';
        var startPage = Math.floor((currentPage - 1) / 5) * 5 + 1;
        var endPage = Math.min(startPage + 4, pageCount);

        if (currentPage > 5) {
            htmlStr += '<a href="#" class="pageLink" data-page="' + (startPage - 1) + '">◀️이전</a>';
        }

        for (var i = startPage; i <= endPage; i++) {
            if (i === currentPage) {
                htmlStr += '<span class="currentPage">' + i + '</span>';
            } else {
                htmlStr += '<a href="#" class="pageLink" data-page="' + i + '">' + i + '</a>';
            }
        }

        if (endPage < pageCount) {
            htmlStr += '<a href="#" class="pageLink" data-page="' + (endPage + 1) + '">다음▶️</a>';
        }

        htmlStr += '</div>';
        modalAddressList.append(htmlStr);

        // Page link click event handler
        $('.pageLink').on('click', function(e) {
            e.preventDefault();
            var page = $(this).data('page');
            searchAddresses(page);
        });
    }
    
    function openModal() {
        $('#addressModal').css('display', 'block');
    }

    function closeModal() {
        $('#addressModal').css('display', 'none');
    }

    $('.close').on('click', function() {
        closeModal();
    });

    $(window).on('click', function(event) {
        if ($(event.target).is('#addressModal')) {
            closeModal();
        }
    });
});
