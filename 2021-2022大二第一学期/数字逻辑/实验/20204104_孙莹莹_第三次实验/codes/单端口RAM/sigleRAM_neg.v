`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/23 09:33:59
// Design Name: 
// Module Name: singleRAM_op
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module sigleRAM_neg#(parameter datawidth=8,adswidth=4,depth=1<<adswidth)(
input clk,
input wen,
input ren,
input [adswidth-1:0]ads,
input [datawidth-1:0]in,
output [datawidth-1:0]out
    );
    reg [datawidth-1:0]memory[0:depth-1];
    reg [datawidth-1:0]outdata;
    integer i;
    initial
    begin
        for (i = 0; i < depth; i=i+1)
            memory[i] = 8'h00;
    end
    always@(posedge clk)begin
        if(wen)begin
            memory[ads]<=in;
        end
    end
    always@(*)begin
        if(wen==0&ren==1)begin
            outdata<= memory[ads];
        end
    end
    assign out=outdata[datawidth-1:0];
endmodule
