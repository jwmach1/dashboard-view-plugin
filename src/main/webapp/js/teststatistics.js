function toggleStatus(statusOrdinal) {
	console.log("toggle status " + statusOrdinal)
	$$('.status'+statusOrdinal).each( function(node) {
		if (node.getStyle('display')==='none'){
			node.show();
			$$('.rollup'+statusOrdinal).each(function(node) {
				node.hide();
			});
		} else {
			node.hide();
			$$('.rollup'+statusOrdinal).each(function(node) {
				node.show();
			});
		}
	});
}