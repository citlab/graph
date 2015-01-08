package socialnetwork;

import java.io.IOException;
import java.util.List;

import org.apache.giraph.edge.Edge;
import org.apache.giraph.edge.EdgeFactory;
import org.apache.giraph.graph.Vertex;
import org.apache.giraph.io.formats.TextVertexInputFormat;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.common.collect.Lists;

public class JsonStringInputFormat extends
		TextVertexInputFormat<LongWritable, StringWritable, FloatWritable> {

	@Override
	public TextVertexReader createVertexReader(InputSplit split,
			TaskAttemptContext context) {
		return new JsonStringVertexReader();
	}

	class JsonStringVertexReader
			extends
			TextVertexReaderFromEachLineProcessedHandlingExceptions<JSONArray, JSONException> {

		@Override
		protected JSONArray preprocessLine(Text line) throws JSONException {
			return new JSONArray(line.toString());
		}

		@Override
		protected LongWritable getId(JSONArray jsonVertex)
				throws JSONException, IOException {
			return new LongWritable(jsonVertex.getLong(0));
		}

		@Override
		protected StringWritable getValue(JSONArray jsonVertex)
				throws JSONException, IOException {
			return new StringWritable(jsonVertex.getString(1));
		}

		@Override
		protected Iterable<Edge<LongWritable, FloatWritable>> getEdges(
				JSONArray jsonVertex) throws JSONException, IOException {
			JSONArray jsonEdgeArray = jsonVertex.getJSONArray(2);
			List<Edge<LongWritable, FloatWritable>> edges = Lists
					.newArrayListWithCapacity(jsonEdgeArray.length());
			for (int i = 0; i < jsonEdgeArray.length(); ++i) {
				JSONArray jsonEdge = jsonEdgeArray.getJSONArray(i);
				edges.add(EdgeFactory.create(
						new LongWritable(jsonEdge.getLong(0)),
						new FloatWritable((float) jsonEdge.getDouble(1))));
			}
			return edges;
		}

		@Override
		protected Vertex<LongWritable, StringWritable, FloatWritable> handleException(
				Text line, JSONArray jsonVertex, JSONException e) {
			throw new IllegalArgumentException("Couldn't get vertex from line "
					+ line, e);
		}
	}

}
