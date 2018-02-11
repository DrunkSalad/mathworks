Object.assign = function( O, dictionary ) {
	var target, src;

	// Let target be ToObject(O).
	target = Object( O );

	  // Let src be ToObject(dictionary).
	  src = Object( dictionary );

	  // For each own property of src, let key be the property key
	  // and desc be the property descriptor of the property.
	  Object.getOwnPropertyNames( src ).forEach(function( key ) {
		target[ key ] = src[ key ];
	  });
	return target;
};

var convert = function(formula) {
	var options = {'parenthesis': 'keep',
					'implicit': 'show'};
    return math.parse(formula).toTex(options);
};